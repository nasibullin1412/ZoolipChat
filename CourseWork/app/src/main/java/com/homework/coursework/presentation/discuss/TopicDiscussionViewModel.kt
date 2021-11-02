package com.homework.coursework.presentation.discuss

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.usecase.GetTopicMessagesUseCase
import com.homework.coursework.domain.usecase.GetTopicMessagesUseCaseImpl
import com.homework.coursework.presentation.MainActivity.Companion.getCurrentUser
import com.homework.coursework.presentation.adapter.data.MessageItem
import com.homework.coursework.presentation.adapter.mapper.MessageItemMapper
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
    private val messageToItemMapper: MessageItemMapper = MessageItemMapper()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * it is emulate sent messages
     */
    private val sentMessages: MutableList<MessageItem> = mutableListOf()

    fun getMessages(idStream: Int, idTopic: Int) {
        getTopicMessagesUseCase(idStream, idTopic)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map(messageToItemMapper)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    _topicDiscScreenState.value = TopicDiscussionState.Result(it + sentMessages)
                },
                onError ={
                    _topicDiscScreenState.value = TopicDiscussionState.Error(it)
                }
            )
            .addTo(compositeDisposable)
    }

    fun addMessage(
        idStream: Int,
        idTopic: Int,
        content: String,
        lastId: Int,
        date: String
    ) {
        sentMessages.add(
            MessageItem(
                id = lastId + 1,
                date = null,
                messageData = MessageData(
                    messageId = lastId + 1,
                    userData = getCurrentUser(),
                    messageContent = content,
                    emojis = arrayListOf(),
                    date = date
                )
            )
        )
        getMessages(idStream, idTopic)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
