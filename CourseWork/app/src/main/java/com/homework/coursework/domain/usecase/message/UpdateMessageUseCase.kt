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
interface UpdateMessageUseCase :
        (StreamData, TopicData, Int) -> Observable<List<MessageData>> {
    override fun invoke(
        streamData: StreamData,
        topicData: TopicData,
        currId: Int
    ): Observable<List<MessageData>>
}

class UpdateMessageUseCaseImpl @Inject constructor(
    private val messageRepository: MessageRepository
) : UpdateMessageUseCase {

    override fun invoke(
        streamData: StreamData,
        topicData: TopicData,
        currId: Int
    ): Observable<List<MessageData>> {
        return messageRepository.updateMessage(
            streamData = streamData,
            topicData = topicData,
            currUserId = currId
        )
    }
}
