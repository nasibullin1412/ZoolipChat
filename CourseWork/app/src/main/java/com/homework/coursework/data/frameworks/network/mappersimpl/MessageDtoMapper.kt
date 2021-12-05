package com.homework.coursework.data.frameworks.network.mappersimpl

import com.homework.coursework.data.dto.MessagesResponse
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.entity.StatusEnum
import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.domain.entity.UserStatus
import dagger.Reusable
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@Reusable
@ExperimentalSerializationApi
class MessageDtoMapper @Inject constructor() : (MessagesResponse, Int) -> (List<MessageData>) {

    @Inject
    internal lateinit var emojiDtoMapper: EmojiDtoMapper

    override fun invoke(messages: MessagesResponse, currUserId: Int): List<MessageData> {
        return messages.data?.map { messageDto ->
            with(messageDto) {
                MessageData(
                    messageId = id,
                    userData = UserData(
                        id = senderId,
                        name = senderFullName,
                        avatarUrl = avatarUrl,
                        userMail = senderEmail,
                        isAdmin = false,
                        userStatus = UserStatus(StatusEnum.UNKNOWN, 0)
                    ),
                    messageContent = content.parseHtmlContent(),
                    emojis = emojiDtoMapper(reactions),
                    date = timestamp,
                    isCurrentUserMessage = senderId == currUserId
                )
            }
        } ?: throw IllegalArgumentException(messages.msg)
    }
}
