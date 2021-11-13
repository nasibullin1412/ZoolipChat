package com.homework.coursework.domain.usecase

import com.homework.coursework.data.StreamRepositoryImpl
import com.homework.coursework.data.TopicRepositoryImpl
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.StreamWithTopics
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.StreamRepository
import com.homework.coursework.domain.repository.TopicRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Used when user want get all streams without any action with search
 */
interface GetAllStreamsUseCase : () -> Single<StreamWithTopics> {
    override fun invoke(): Single<StreamWithTopics>
}

class GetAllStreamsUseCaseImpl : GetAllStreamsUseCase {

    private val streamRepository: StreamRepository = StreamRepositoryImpl()
    private val topicRepository: TopicRepository = TopicRepositoryImpl()

    override fun invoke(): Single<StreamWithTopics> {
        return streamRepository.loadStreams()
            .subscribeOn(Schedulers.io())
            .switchMap { streamList ->
                Observable.fromIterable(streamList)
            }
            .concatMap { streamData ->
                Observable.zip(
                    Observable.just(streamData),
                    topicRepository.loadTopics(streamData.id)
                )
                { stream: StreamData, topicList: List<TopicData> ->
                    Pair(stream, topicList)
                }
            }
            .toList()
    }
}
