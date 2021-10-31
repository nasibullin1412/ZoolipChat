package com.homework.coursework.presentation.discuss

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.usecase.GetTopicMessagesUseCase
import com.homework.coursework.domain.usecase.GetTopicMessagesUseCaseImpl
import com.homework.coursework.presentation.MainActivity.Companion.getCurrentUser
import com.homework.coursework.presentation.adapter.mapper.MessageItemMapper

class TopicDiscussionViewModel : ViewModel() {
    private var _topicDiscScreenState: MutableLiveData<TopicDiscussionState> = MutableLiveData()
    val topicDiscScreenState: LiveData<TopicDiscussionState>
        get() = _topicDiscScreenState

    private val getTopicMessagesUseCase: GetTopicMessagesUseCase = GetTopicMessagesUseCaseImpl()
    private val messageToItemMapper: MessageItemMapper = MessageItemMapper()

    /**
     * it is emulate sent messages
     */
    private val sentMessages: MutableList<MessageData> = mutableListOf()

    fun getMessages(idStream: Int, idTopic: Int) {
        _topicDiscScreenState.value =
            TopicDiscussionState.Result(
                messageToItemMapper(
                    getTopicMessagesUseCase(
                        idStream,
                        idTopic
                    ) + sentMessages
                )
            )

    }

    fun addMessage(
        idStream: Int,
        idTopic: Int,
        content: String,
        lastId: Int,
        date: String
    ) {
        sentMessages.add(
            MessageData(
                messageId = lastId + 1,
                userData = getCurrentUser(),
                messageContent = content,
                emojis = arrayListOf(),
                date = date
            )
        )
        getMessages(idStream, idTopic)
    }
}
