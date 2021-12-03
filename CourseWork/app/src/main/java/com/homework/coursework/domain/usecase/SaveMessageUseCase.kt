package com.homework.coursework.domain.usecase

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.MessageRepository
import io.reactivex.Completable
import javax.inject.Inject

interface SaveMessageUseCase : (StreamData, TopicData, List<MessageData>, Int) -> Completable {
    override fun invoke(
        streamData: StreamData,
        topicData: TopicData,
        messageDataList: List<MessageData>,
        currId: Int
    ): Completable
}

class SaveMessageUseCaseImpl @Inject constructor(
    private val repositoryMessage: MessageRepository
) : SaveMessageUseCase {

    override fun invoke(
        streamData: StreamData,
        topicData: TopicData,
        messageDataList: List<MessageData>,
        currId: Int
    ): Completable {
        return repositoryMessage.saveMessages(
            streamData = streamData,
            topicData = topicData,
            messages = messageDataList,
            currUserId = currId
        )
    }
}
