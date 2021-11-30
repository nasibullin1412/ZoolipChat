package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.USER_ID
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.presentation.adapter.data.MessageItem
import dagger.Reusable
import javax.inject.Inject

@Reusable
class MessageItemMapper @Inject constructor() : (List<MessageData>) -> (List<MessageItem>) {

    @Inject
    internal lateinit var emojiItemMapper: EmojiItemMapper

    override fun invoke(messagesData: List<MessageData>): List<MessageItem> {
        return messagesData.map { message ->
            with(message) {
                MessageItem(
                    messageId = messageId,
                    userData = userData,
                    messageContent = messageContent,
                    emojis = emojiItemMapper(emojis, USER_ID),
                    date = date,
                    isCurrentUserMessage = isCurrentUserMessage
                )
            }
        }
    }
}
