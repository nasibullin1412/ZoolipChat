package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.presentation.adapter.data.MessageItem

class MessageDataMapper : (MessageItem) -> (MessageData) {
    override fun invoke(messageItem: MessageItem): MessageData {
        return with(messageItem) {
            MessageData(
                messageId = messageId,
                userData = userData,
                messageContent = messageContent,
                emojis = listOf(),
                isCurrentUserMessage = isCurrentUserMessage,
                date = date
            )
        }
    }
}
