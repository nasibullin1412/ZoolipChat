package com.homework.coursework.domain.usecase.message

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.repository.MessageRepository
import io.reactivex.Completable
import javax.inject.Inject

interface DeleteMessageUseCase: (MessageData) -> (Completable){
    override fun invoke(messageData: MessageData): Completable
}

class DeleteMessageUseCaseImpl @Inject constructor(
    private val messageRepository: MessageRepository
): DeleteMessageUseCase {
    override fun invoke(messageData: MessageData): Completable {
        return messageRepository.deleteMessage(messageData)
    }
}
