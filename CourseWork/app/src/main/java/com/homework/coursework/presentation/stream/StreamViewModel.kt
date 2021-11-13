package com.homework.coursework.presentation.stream

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.coursework.domain.usecase.*
import com.homework.coursework.presentation.adapter.mapper.StreamItemMapper
import com.homework.coursework.presentation.utils.getStreamFragmentUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class StreamViewModel : ViewModel() {
    private var _streamScreenState: MutableLiveData<StreamScreenState> = MutableLiveData()
    val streamScreenState: LiveData<StreamScreenState>
        get() = _streamScreenState

    private val getAllStreamsUseCase: GetAllStreamsUseCase = GetAllStreamsUseCaseImpl()
    private val getSubscribedStreamsUseCase: GetSubscribedStreamsUseCase =
        GetSubscribedStreamsUseCaseImpl()
    private val streamToItemMapper: StreamItemMapper = StreamItemMapper()
    private val searchAllStreamUseCase: SearchAllStreamsUseCase = SearchAllStreamsUseCaseImpl()
    private val searchSubscribedStreamsUseCase: SearchSubscribeStreamsUseCase =
        SearchSubscribeStreamsUseCaseImpl()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val searchSubject: PublishSubject<Pair<Int, String>> = PublishSubject.create()

    init {
        subscribeToSearchStreams()
    }

    fun searchStreams(tabState: Int, searchQuery: String) {
        searchSubject.onNext(Pair(tabState, searchQuery))
    }

    private fun subscribeToSearchStreams() {
        searchSubject
            .subscribeOn(Schedulers.io())
            .distinctUntilChanged()
            .doOnNext { _streamScreenState.postValue(StreamScreenState.Loading) }
            .debounce(500, TimeUnit.MILLISECONDS, Schedulers.io())
            .switchMapSingle { pair ->
                getNeedUseCase(
                    tabState = pair.first,
                    query = pair.second
                )
            }
            .map(streamToItemMapper)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { _streamScreenState.value = StreamScreenState.Result(it) },
                onError = {
                    _streamScreenState.value = StreamScreenState.Error(it)
                    subscribeToSearchStreams()
                }
            )
            .addTo(compositeDisposable)
    }

    fun getStreams(tabState: Int) {
        getNeedUseCase(tabState)
            .map(streamToItemMapper)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _streamScreenState.value = StreamScreenState.Result(it)
                },
                onError = { _streamScreenState.value = StreamScreenState.Error(it) }
            )
            .addTo(compositeDisposable)
    }


    private fun getNeedUseCase(tabState: Int, query: String? = null) =
        when (tabState.getStreamFragmentUseCase(query)) {
            UseCaseType.GET_ALL_STREAMS_USE_CASE -> getAllStreamsUseCase()
            UseCaseType.GET_SUBSCRIBED_STREAM_USE_CASE -> getSubscribedStreamsUseCase()
            UseCaseType.SEARCH_ALL_STREAM_USE_CASE -> searchAllStreamUseCase(query ?: "")
            UseCaseType.SEARCH_SUBSCRIBED_USE_CASE -> searchSubscribedStreamsUseCase(
                query ?: ""
            )
        }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
