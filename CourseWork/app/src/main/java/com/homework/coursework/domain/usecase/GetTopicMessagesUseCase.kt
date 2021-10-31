package com.homework.coursework.domain.usecase

import com.homework.coursework.data.MessageRepositoryImpl
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.repository.MessageRepository

interface GetTopicMessagesUseCase : (Int, Int) -> List<MessageData> {
    override fun invoke(idStream: Int, idTopic: Int): List<MessageData>
}

class GetTopicMessagesUseCaseImpl : GetTopicMessagesUseCase {

    private val messageRepository: MessageRepository = MessageRepositoryImpl()

    override fun invoke(idStream: Int, idTopic: Int): List<MessageData> {
        return messageRepository.loadMessages(idStream, idTopic)
    }
}
