package com.homework.coursework.data

import com.homework.coursework.data.mappers.TopicDtoMapper
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.TopicRepository
import com.homework.coursework.presentation.App
import io.reactivex.Observable
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class TopicRepositoryImpl : TopicRepository {

    private val topicDtoMapper: TopicDtoMapper = TopicDtoMapper()

    override fun loadTopics(idStream: Int): Observable<List<TopicData>> {
        return App.instance.apiService.getTopics(idStream)
            .switchMap {
                Observable.fromCallable {
                    topicDtoMapper(it)
                }
            }
    }
}
