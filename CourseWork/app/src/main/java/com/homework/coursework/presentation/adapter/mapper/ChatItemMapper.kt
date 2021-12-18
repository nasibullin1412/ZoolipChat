package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.presentation.adapter.data.chat.*
import com.homework.coursework.presentation.utils.toStringDate
import dagger.Reusable
import java.util.*
import javax.inject.Inject

@Reusable
class ChatItemMapper @Inject constructor() : (List<MessageData>, Int, Boolean) -> (List<ChatItem>) {

    @Inject
    internal lateinit var messageItemMapper: MessageItemMapper

    @Inject
    internal lateinit var chatAddTopicNames: ChatAddTopicNames

    override fun invoke(
        messageDataList: List<MessageData>,
        currId: Int,
        isUpdate: Boolean
    ): List<ChatItem> {
        if (messageDataList.size == 1 && messageDataList.first().errorHandle.isError) {
            return messageDataList.first().errorHandle.run {
                listOf(ChatItem.getErrorChatItem(error))
            }
        }
        val messageDataIds = chatAddTopicNames(messageDataList)
        return messageDataList.groupBy { it.date.toStringDate() }.run {
            val messageList = arrayListOf<ChatItem>()
            for (date in keys) {
                messageList.addDate(date)
                val list = this[date] ?: throw IllegalArgumentException("message required")
                messageList.addMessageItem(
                    messageItemMapper(list, currId, messageDataList.lastIndex + 1)
                )
            }
            messageList.apply {
                if (!isUpdate) addTopicNames(messageDataIds)
                forEachIndexed { index, item -> item.id = index }
            }
        }
    }
}

/**
 * add date to recycler list
 * @param date is string with date
 */
fun ArrayList<ChatItem>.addDate(date: String) {
    if (lastIndex == -1) {
        return
    }
    val item = this[lastIndex]
    if (item is MessageItem) {
        if (item.date.toStringDate() == date) {
            return
        }
    }
    add(DateItem(date = date))
}

/**
 * add message to recycler list
 * @param messageItemList is list with new messages
 */
fun ArrayList<ChatItem>.addMessageItem(messageItemList: List<MessageItem>) {
    addAll(
        messageItemList.map { messageItem ->
            with(messageItem) {
                MessageItem(
                    messageId = messageId,
                    userItem = userItem,
                    messageContent = messageContent,
                    emojis = emojis,
                    date = date,
                    isCurrentUserMessage = isCurrentUserMessage,
                    topicName = topicName
                )
            }
        }
    )
}

fun ArrayList<ChatItem>.addTopicNames(messageIdList: ArrayList<Int>) {
    for (messageId in messageIdList) {
        val messageItem = firstWithMessageItem { it == messageId } ?: return
        val idx = indexOf(messageItem)
        add(index = idx, element = TopicNameItem(topicName = messageItem.topicName))
    }
}
