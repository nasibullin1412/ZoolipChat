package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.MessageEntity
import com.homework.coursework.data.frameworks.database.entities.MessageWithEmojiEntity
import com.homework.coursework.domain.entity.MessageData

class MessageDataMapper : (List<MessageData>, String, Int) -> (List<MessageWithEmojiEntity>) {

    private val emojiDataMapper: EmojiDataMapper = EmojiDataMapper()
    private val userDataMapper: UserDataMapper = UserDataMapper()

    override fun invoke(
        messageDataList: List<MessageData>,
        topicName: String,
        streamId: Int
    ): List<MessageWithEmojiEntity> {
        return messageDataList.map { messageData ->
            with(messageData) {
                MessageWithEmojiEntity(
                    messageEntity = MessageEntity(
                        id = 0,
                        messageId = messageId,
                        userId = userData.id,
                        messageContent = messageContent,
                        date = date,
                        isCurrentUserMessage = isCurrentUserMessage,
                        topicName = topicName,
                        streamId = streamId
                    ),
                    emojiEntity = emojiDataMapper(emojis, messageId),
                    userEntity = userDataMapper(userData)
                )
            }
        }
    }
}