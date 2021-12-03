package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.presentation.adapter.data.MessageItem
import dagger.Reusable
import javax.inject.Inject

@Reusable
class MessageItemMapper @Inject constructor() : (List<MessageData>, Int) -> (List<MessageItem>) {

    @Inject
    internal lateinit var emojiItemMapper: EmojiItemMapper

    override fun invoke(messagesData: List<MessageData>, currId: Int): List<MessageItem> {
        return messagesData.map { message ->
            with(message) {
                MessageItem(
                    messageId = messageId,
                    userData = userData,
                    messageContent = messageContent,
                    emojis = emojiItemMapper(emojis, currId),
                    date = date,
                    isCurrentUserMessage = isCurrentUserMessage
                )
            }
        }
    }
}
