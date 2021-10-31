package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.presentation.adapter.data.MessageItem
import com.homework.coursework.presentation.utils.addDate
import com.homework.coursework.presentation.utils.addMessageData

class MessageItemMapper : (List<MessageData>) -> (List<MessageItem>) {

    override fun invoke(messadeDatas: List<MessageData>): List<MessageItem> {
        val messageList = arrayListOf<MessageItem>()
        val groupedMessage = messadeDatas.groupBy { it.date }
        with(groupedMessage) {
            for (date in keys) {
                messageList.addDate(date)
                messageList.addMessageData(
                    this[date]
                        ?: throw IllegalArgumentException("message required")
                )
            }
        }
        return messageList
    }
}
