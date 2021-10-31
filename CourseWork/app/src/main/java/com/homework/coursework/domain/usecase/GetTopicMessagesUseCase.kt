package com.homework.coursework.domain.usecase

import com.homework.coursework.data.MessageRepositoryImpl
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.MessageRepository
import io.reactivex.Observable

interface GetTopicMessagesUseCase : (Int, Int) -> Observable<List<MessageData>> {
    override fun invoke(idStream: Int, idTopic: Int): Observable<List<MessageData>>
}

class GetTopicMessagesUseCaseImpl : GetTopicMessagesUseCase {

    private val messageRepository: MessageRepository = MessageRepositoryImpl()

    override fun invoke(idStream: Int, idTopic: Int): Observable<List<MessageData>> {
        return messageRepository.loadMessages(idStream, idTopic)
    }
}
