package com.homework.coursework.domain.usecase


import com.homework.coursework.data.MessageRepositoryImpl
import com.homework.coursework.di.GlobalDI
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.MessageRepository
import io.reactivex.Observable

/**
 * Used when user want see messages of topic
 */
interface InitMessageUseCase : (StreamData, TopicData) -> Observable<List<MessageData>> {
    override fun invoke(streamData: StreamData, topicData: TopicData): Observable<List<MessageData>>
}

class InitMessageUseCaseImpl(
    private val messageRepository: MessageRepository
): InitMessageUseCase {

    override fun invoke(
        streamData: StreamData,
        topicData: TopicData
    ): Observable<List<MessageData>> {
        return messageRepository.initMessages(streamData, topicData)
    }
}
