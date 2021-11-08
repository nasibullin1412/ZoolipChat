package com.homework.coursework.presentation.discuss

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.usecase.GetTopicMessagesUseCase
import com.homework.coursework.domain.usecase.GetTopicMessagesUseCaseImpl
import com.homework.coursework.presentation.adapter.data.DiscussItem
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.adapter.mapper.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class TopicDiscussionViewModel : ViewModel() {
    private var _topicDiscScreenState: MutableLiveData<TopicDiscussionState> = MutableLiveData()
    val topicDiscScreenState: LiveData<TopicDiscussionState>
        get() = _topicDiscScreenState

    private val getTopicMessagesUseCase: GetTopicMessagesUseCase = GetTopicMessagesUseCaseImpl()
    private val discussToItemMapper: DiscussItemMapper = DiscussItemMapper()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val streamDataMapper: StreamDataMapper = StreamDataMapper()
    private val topicDataMapper: TopicDataMapper = TopicDataMapper()

    /**
     * it is emulate sent messages
     */
    private val sentDiscusses: MutableList<DiscussItem> = mutableListOf()

    fun getMessages(stream: StreamItem, topic: TopicItem) {
        getTopicMessagesUseCase(streamDataMapper(stream), topicDataMapper(topic))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map(discussToItemMapper)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    _topicDiscScreenState.value = TopicDiscussionState.Result(it + sentDiscusses)
                },
                onError = {
                    _topicDiscScreenState.value = TopicDiscussionState.Error(it)
                }
            )
            .addTo(compositeDisposable)
    }

    /*fun addMessage(

    ) {
        getMessages(idStream, idTopic)
    }*/

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
