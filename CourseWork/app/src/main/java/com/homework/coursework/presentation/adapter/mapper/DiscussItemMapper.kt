package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.presentation.adapter.data.ChatItem
import com.homework.coursework.presentation.adapter.data.ErrorHandle
import com.homework.coursework.presentation.ui.chat.addDate
import com.homework.coursework.presentation.ui.chat.addMessageItem
import com.homework.coursework.presentation.utils.toStringDate
import dagger.Reusable
import javax.inject.Inject

@Reusable
class DiscussItemMapper @Inject constructor() : (List<MessageData>, Int) -> (List<ChatItem>) {

    @Inject
    internal lateinit var messageItemMapper: MessageItemMapper

    override fun invoke(messageDataList: List<MessageData>, currId: Int): List<ChatItem> {
        if (messageDataList.size == 1 && messageDataList.first().errorHandle.isError) {
            val erHandle = messageDataList.first().errorHandle
            return listOf(
                ChatItem(
                    id = 0,
                    messageItem = null,
                    date = null,
                    errorHandle = ErrorHandle(
                        isError = erHandle.isError, error = erHandle.error
                    )
                )
            )
        }
        val messageList = arrayListOf<ChatItem>()
        val groupedMessage = messageDataList.groupBy { it.date.toStringDate() }
        with(groupedMessage) {
            for (date in keys) {
                messageList.addDate(date)
                val list =
                    this[date] ?: throw IllegalArgumentException("message required")
                messageList.addMessageItem(
                    messageItemMapper(list, currId)
                )
            }
        }
        return messageList
    }
}
