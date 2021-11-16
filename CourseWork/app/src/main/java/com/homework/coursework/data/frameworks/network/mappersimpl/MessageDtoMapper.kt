package com.homework.coursework.data.frameworks.network.mappersimpl

import com.homework.coursework.data.dto.MessagesResponse
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.USER_ID
import com.homework.coursework.data.mappers.MessageMapper
import com.homework.coursework.domain.entity.StatusEnum
import com.homework.coursework.domain.entity.UserStatus
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MessageDtoMapper : MessageMapper<MessagesResponse> {

    private val emojiDtoMapper: EmojiDtoMapper = EmojiDtoMapper()

    override fun invoke(messages: MessagesResponse): List<MessageData> {
        return messages.data?.map { messageDto ->
            with(messageDto) {
                MessageData(
                    messageId = id,
                    userData = UserData(
                        id = senderId,
                        name = senderFullName,
                        avatarUrl = avatarUrl,
                        userMail = senderEmail,
                        UserStatus(StatusEnum.UNKNOWN, 0)
                    ),
                    messageContent = content.parseHtmlContent(),
                    emojis = emojiDtoMapper(reactions),
                    date = timestamp,
                    isCurrentUserMessage = senderId == USER_ID
                )
            }
        } ?: throw IllegalArgumentException(messages.msg)
    }
}
