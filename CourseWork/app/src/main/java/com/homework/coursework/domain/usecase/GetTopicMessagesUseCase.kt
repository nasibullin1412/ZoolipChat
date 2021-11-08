package com.homework.coursework.domain.usecase

import com.homework.coursework.data.MessageRepositoryImpl
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.MessageRepository
import io.reactivex.Observable

/**
 * Used when user want see messages of topic
 */
interface GetTopicMessagesUseCase : (StreamData, TopicData) -> Observable<List<MessageData>> {
    override fun invoke(streamData: StreamData, topicData: TopicData): Observable<List<MessageData>>
}

class GetTopicMessagesUseCaseImpl : GetTopicMessagesUseCase {

    private val messageRepository: MessageRepository = MessageRepositoryImpl()

    override fun invoke(
        streamData: StreamData,
        topicData: TopicData
    ): Observable<List<MessageData>> {
        return messageRepository.loadMessages(streamData, topicData)
    }
}
