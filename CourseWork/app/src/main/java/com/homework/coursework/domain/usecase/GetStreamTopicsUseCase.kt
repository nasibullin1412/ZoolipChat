package com.homework.coursework.domain.usecase

import com.homework.coursework.data.TopicRepositoryImpl
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.TopicRepository


interface GetStreamTopicsUseCase : (Int) -> List<TopicData> {
    override fun invoke(idStream: Int): List<TopicData>
}

class GetStreamTopicsUseCaseImpl: GetStreamTopicsUseCase {
    private val topicRepository: TopicRepository = TopicRepositoryImpl()

    override fun invoke(idStream: Int): List<TopicData> {
        return topicRepository.loadTopics(idStream)
    }
}
