package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.presentation.adapter.data.MessageItem
import dagger.Reusable
import javax.inject.Inject

@Reusable
class MessageItemMapper @Inject constructor() :
        (List<MessageData>, Int, Int) -> (List<MessageItem>) {

    @Inject
    internal lateinit var emojiItemMapper: EmojiItemMapper

    override fun invoke(
        messagesData: List<MessageData>,
        currId: Int,
        chatItemId: Int
    ): List<MessageItem> {
        return messagesData.mapIndexed { idx, message ->
            with(message) {
                MessageItem(
                    idItem = chatItemId + idx,
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
