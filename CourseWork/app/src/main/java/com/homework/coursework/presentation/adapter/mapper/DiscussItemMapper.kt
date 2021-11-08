package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.presentation.adapter.data.DiscussItem
import com.homework.coursework.presentation.utils.addDate
import com.homework.coursework.presentation.utils.addMessageItem
import com.homework.coursework.presentation.utils.toStringDate

class DiscussItemMapper : (List<MessageData>) -> (List<DiscussItem>) {

    private val messageItemMapper: MessageItemMapper = MessageItemMapper()

    override fun invoke(messadeDatas: List<MessageData>): List<DiscussItem> {
        val messageList = arrayListOf<DiscussItem>()
        val groupedMessage = messadeDatas.groupBy { it.date.toStringDate() }
        with(groupedMessage) {
            for (date in keys) {
                messageList.addDate(date)
                val messageDataList =
                    this[date] ?: throw IllegalArgumentException("message required")
                messageList.addMessageItem(
                    messageItemMapper(messageDataList)
                )
            }
        }
        return messageList
    }
}
