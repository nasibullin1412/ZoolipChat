package com.homework.coursework.domain.usecase

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.MessageRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Used when user want see messages of topic
 */
interface GetTopicMessagesUseCase : (StreamData, TopicData, Int) -> Observable<List<MessageData>> {
    override fun invoke(
        streamData: StreamData,
        topicData: TopicData,
        currId: Int
    ): Observable<List<MessageData>>
}

class GetTopicMessagesUseCaseImpl @Inject constructor(
    private val messageRepository: MessageRepository
) : GetTopicMessagesUseCase {

    override fun invoke(
        streamData: StreamData,
        topicData: TopicData,
        currId: Int
    ): Observable<List<MessageData>> {
        return messageRepository.loadMessages(streamData, topicData, currId)
    }
}
