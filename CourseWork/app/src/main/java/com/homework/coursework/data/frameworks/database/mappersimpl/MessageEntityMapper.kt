package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.MessageWithEmojiEntity
import com.homework.coursework.domain.entity.MessageData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class MessageEntityMapper @Inject constructor() : (List<MessageWithEmojiEntity>) -> (List<MessageData>) {

    private val emojiEntityMapper: EmojiEntityMapper = EmojiEntityMapper()
    private val userEntityMapper: UserEntityMapper = UserEntityMapper()

    override fun invoke(messages: List<MessageWithEmojiEntity>): List<MessageData> {
        return messages.map { messageWithEmojiEntity ->
            with(messageWithEmojiEntity) {
                MessageData(
                    messageId = messageEntity.messageId,
                    userData = userEntityMapper(userEntity),
                    messageContent = messageEntity.messageContent,
                    emojis = emojiEntityMapper(emojiEntity),
                    date = messageEntity.date,
                    isCurrentUserMessage = messageEntity.isCurrentUserMessage
                )
            }
        }
    }
}
