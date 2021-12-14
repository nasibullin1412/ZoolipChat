package com.homework.coursework.domain.usecase.message

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.MessageRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Used when user want see messages of topic
 */
interface InitMessageUseCase : (StreamData, TopicData, Int) -> Observable<List<MessageData>> {
    override fun invoke(
        streamData: StreamData,
        topicData: TopicData,
        currId: Int
    ): Observable<List<MessageData>>
}

class InitMessageUseCaseImpl @Inject constructor(
    private val messageRepository: MessageRepository
) : InitMessageUseCase {

    override fun invoke(
        streamData: StreamData,
        topicData: TopicData,
        currId: Int
    ): Observable<List<MessageData>> {
        return messageRepository.initMessages(streamData, topicData, currId)
    }
}
