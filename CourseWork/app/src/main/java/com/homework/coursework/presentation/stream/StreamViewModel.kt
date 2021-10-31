package com.homework.coursework.presentation.stream

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.coursework.domain.usecase.*
import com.homework.coursework.presentation.adapter.mapper.StreamItemMapper
import com.homework.coursework.presentation.adapter.mapper.TopicItemMapper
import com.homework.coursework.presentation.discuss.TopicDiscussionState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class StreamViewModel : ViewModel() {
    private var _streamScreenState: MutableLiveData<StreamScreenState> = MutableLiveData()
    val streamScreenState: LiveData<StreamScreenState>
        get() = _streamScreenState

    private var _topicScreenState: MutableLiveData<TopicScreenState> = MutableLiveData()
    val topicScreenState: LiveData<TopicScreenState>
        get() = _topicScreenState

    private val getAllStreamsUseCase: GetAllStreamsUseCase = GetAllStreamsUseCaseImpl()
    private val getSubscribedStreamsUseCase: GetSubscribedStreamsUseCase =
        GetSubscribedStreamsUseCaseImpl()
    private val streamToItemMapper: StreamItemMapper = StreamItemMapper()

    private val getStreamTopicsUseCase: GetStreamTopicsUseCase = GetStreamTopicsUseCaseImpl()
    private val topicToItemMapper: TopicItemMapper = TopicItemMapper()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getTopics(idStream: Int) {
        getStreamTopicsUseCase(idStream)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map(topicToItemMapper)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { _topicScreenState.value = TopicScreenState.Result(it, idStream) }
            )
            .addTo(compositeDisposable)
    }

    fun getStreams(isSubscribed: Boolean) {
        getNeedUseCase(isSubscribed)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map(streamToItemMapper)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    _streamScreenState.value = StreamScreenState.Result(it)
                }
            )
            .addTo(compositeDisposable)
    }

    private fun getNeedUseCase(isSubscribed: Boolean) = if (isSubscribed) {
        getAllStreamsUseCase()
    } else {
        getSubscribedStreamsUseCase()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
