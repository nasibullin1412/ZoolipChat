package com.homework.coursework.data

import com.homework.coursework.data.dto.StreamDto
import com.homework.coursework.data.dto.StreamWithTopics
import com.homework.coursework.data.dto.TopicDto
import com.homework.coursework.data.mappers.StreamDtoMapper
import com.homework.coursework.data.mappers.TopicDtoMapper
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.StreamRepository
import com.homework.coursework.presentation.App
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class StreamRepositoryImpl : StreamRepository {

    private val streamDtoMapper: StreamDtoMapper = StreamDtoMapper()
    private val topicDtoMapper: TopicDtoMapper = TopicDtoMapper()

    override fun loadStreams(): Single<List<StreamData>> {
        return App.instance.apiService.getAllStreams()
            .subscribeOn(Schedulers.io())
            .switchMap { streamList -> Observable.fromIterable(streamList.data) }
            .concatMap { streamDto -> zipStreamAndTopics(streamDto) }
            .toList()
            .map(streamDtoMapper)
    }

    override fun loadSubscribedStreams(): Single<List<StreamData>> {
        return App.instance.apiService.getSubscribedStreams()
            .subscribeOn(Schedulers.io())
            .switchMap { streamList -> Observable.fromIterable(streamList.data) }
            .concatMap { streamDto -> zipStreamAndTopics(streamDto) }
            .toList()
            .map(streamDtoMapper)
    }

    override fun loadTopics(idStream: Int): Observable<List<TopicDto>> {
        return App.instance.apiService.getTopics(idStream)
            .map { it.data }
    }

    private fun zipStreamAndTopics(streamDto: StreamDto): Observable<StreamWithTopics> {
        return Observable.zip(
            Observable.just(streamDto),
            loadTopics(streamDto.streamId)
        )
        { stream: StreamDto, topicList: List<TopicDto> ->
            Pair(stream, topicList)
        }
    }
}
