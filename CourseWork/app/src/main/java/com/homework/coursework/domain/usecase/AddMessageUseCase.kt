package com.homework.coursework.domain.usecase

import com.homework.coursework.data.MessageRepositoryImpl
import com.homework.coursework.di.GlobalDI
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.MessageRepository
import io.reactivex.Observable

interface AddMessageUseCase : (StreamData, TopicData, String) -> Observable<Int> {
    override fun invoke(
        streamData: StreamData,
        topicData: TopicData,
        content: String
    ): Observable<Int>
}

class AddMessageUseCaseImpl(
    private val repositoryMessage: MessageRepository
) : AddMessageUseCase {

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
