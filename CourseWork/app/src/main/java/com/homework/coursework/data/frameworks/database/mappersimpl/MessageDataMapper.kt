package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.MessageEntity
import com.homework.coursework.data.frameworks.database.entities.MessageWithEmojiEntity
import com.homework.coursework.domain.entity.MessageData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class MessageDataMapper @Inject constructor() :
        (List<MessageData>, String, Int, Int) -> (List<MessageWithEmojiEntity>) {

    @Inject
    internal lateinit var emojiDataMapper: EmojiDataMapper

    @Inject
    internal lateinit var userDataMapper: UserDataMapper

    override fun invoke(
        messageDataList: List<MessageData>,
        topicName: String,
        streamId: Int,
        currUserId: Int
    ): List<MessageWithEmojiEntity> {
        return messageDataList.map { messageData ->
            messageData.run {
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
                    emojiEntity = emojiDataMapper(emojis, messageId, currUserId),
                    userEntity = userDataMapper(userData)
                )
            }
        }
    }
}
