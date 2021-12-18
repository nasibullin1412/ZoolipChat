package com.homework.coursework.domain.usecase.message

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.MessageRepository
import io.reactivex.Completable
import javax.inject.Inject

interface EditMessageUseCase : (MessageData) -> (Completable) {
    override fun invoke(messageData: MessageData): Completable
}

class EditMessageUseCaseImpl @Inject constructor(
    private val messageRepository: MessageRepository
) : EditMessageUseCase {
    override fun invoke(messageData: MessageData): Completable {
        return messageRepository.editMessage(messageData = messageData)
    }
}
