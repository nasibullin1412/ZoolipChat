package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.presentation.adapter.data.chat.MessageItem
import dagger.Reusable
import javax.inject.Inject

@Reusable
class MessageDataMapper @Inject constructor() : (MessageItem) -> (MessageData) {

    @Inject
    internal lateinit var emojiDataListMapper: EmojiDataListMapper

    @Inject
    internal lateinit var userDataMapper: UserDataMapper

    override fun invoke(messageItem: MessageItem): MessageData {
        return with(messageItem) {
            MessageData(
                messageId = messageId,
                userData = userDataMapper(userItem),
                messageContent = messageContent,
                emojis = emojiDataListMapper(emojis),
                isCurrentUserMessage = isCurrentUserMessage,
                date = date,
                topicName = topicName
            )
        }
    }
}
