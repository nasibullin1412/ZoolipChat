package com.homework.coursework.domain.usecase

import com.homework.coursework.data.MessageRepositoryImpl
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.MessageRepository
import io.reactivex.Completable

interface AddMessageUseCase : (StreamData, TopicData, String) -> Completable {
    override fun invoke(streamData: StreamData, topicData: TopicData, content: String): Completable
}

class AddMessageUseCaseImpl : AddMessageUseCase {

    private val repositoryMessage: MessageRepository = MessageRepositoryImpl()

    override fun invoke(
        streamData: StreamData,
        topicData: TopicData,
        content: String
    ): Completable {
        return repositoryMessage.addMessages(
            streamData = streamData,
            topicData = topicData,
            content = content
        )
    }
}
