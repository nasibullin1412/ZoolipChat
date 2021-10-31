package com.homework.coursework.domain.usecase

import com.homework.coursework.data.TopicRepositoryImpl
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.TopicRepository
import io.reactivex.Observable

interface GetStreamTopicsUseCase : (Int) -> Observable<List<TopicData>> {
    override fun invoke(idStream: Int): Observable<List<TopicData>>
}

class GetStreamTopicsUseCaseImpl : GetStreamTopicsUseCase {
    private val topicRepository: TopicRepository = TopicRepositoryImpl()

    override fun invoke(idStream: Int): Observable<List<TopicData>> {
        return topicRepository.loadTopics(idStream)
    }
}
