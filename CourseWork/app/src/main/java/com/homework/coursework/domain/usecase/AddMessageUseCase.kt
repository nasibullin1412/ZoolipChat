package com.homework.coursework.domain.usecase

import com.homework.coursework.data.MessageRepositoryImpl
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.MessageRepository
import io.reactivex.Observable
import io.reactivex.Single

interface AddMessageUseCase : (StreamData, TopicData, String) -> Observable<Int> {
    override fun invoke(streamData: StreamData, topicData: TopicData, content: String): Observable<Int>
}

class AddMessageUseCaseImpl : AddMessageUseCase {

    private val repositoryMessage: MessageRepository = MessageRepositoryImpl()

    override fun invoke(
        streamData: StreamData,
        topicData: TopicData,
        content: String
    ): Observable<Int> {
        return repositoryMessage.addMessages(
            streamData = streamData,
            topicData = topicData,
            content = content
        )
    }
}
