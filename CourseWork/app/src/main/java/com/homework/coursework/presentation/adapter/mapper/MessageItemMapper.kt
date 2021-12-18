package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.presentation.adapter.data.chat.MessageItem
import dagger.Reusable
import javax.inject.Inject

@Reusable
class MessageItemMapper @Inject constructor() :
        (List<MessageData>, Int, Int) -> (List<MessageItem>) {

    @Inject
    internal lateinit var emojiItemMapper: EmojiItemMapper

    @Inject
    internal lateinit var userItemMapper: UserItemMapper

    override fun invoke(
        messagesData: List<MessageData>,
        currId: Int,
        chatItemId: Int
    ): List<MessageItem> {
        return messagesData.map { message ->
            with(message) {
                MessageItem(
                    messageId = messageId,
                    userItem = userItemMapper(userData, 0),
                    messageContent = messageContent,
                    emojis = emojiItemMapper(emojis, currId),
                    date = date,
                    isCurrentUserMessage = isCurrentUserMessage,
                    topicName = topicName
                )
            }
        }
    }
}
