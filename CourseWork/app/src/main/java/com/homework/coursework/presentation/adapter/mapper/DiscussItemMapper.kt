package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.presentation.adapter.data.DiscussItem
import com.homework.coursework.presentation.adapter.data.ErrorHandle
import com.homework.coursework.presentation.discuss.addDate
import com.homework.coursework.presentation.discuss.addMessageItem
import com.homework.coursework.presentation.utils.toStringDate

class DiscussItemMapper : (List<MessageData>) -> (List<DiscussItem>) {

    private val messageItemMapper: MessageItemMapper = MessageItemMapper()

    override fun invoke(messageDataList: List<MessageData>): List<DiscussItem> {
        if (messageDataList.size == 1 && messageDataList.first().errorHandle.isError) {
            val erHandle = messageDataList.first().errorHandle
            return listOf(
                DiscussItem(
                    id = 0,
                    messageItem = null,
                    date = null,
                    errorHandle = ErrorHandle(
                        isError = erHandle.isError, error = erHandle.error
                    )
                )
            )
        }
        val messageList = arrayListOf<DiscussItem>()
        val groupedMessage = messageDataList.groupBy { it.date.toStringDate() }
        with(groupedMessage) {
            for (date in keys) {
                messageList.addDate(date)
                val list =
                    this[date] ?: throw IllegalArgumentException("message required")
                messageList.addMessageItem(
                    messageItemMapper(list)
                )
            }
        }
        return messageList
    }
}
