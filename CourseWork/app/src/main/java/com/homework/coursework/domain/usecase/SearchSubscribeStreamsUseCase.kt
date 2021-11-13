package com.homework.coursework.domain.usecase

import com.homework.coursework.data.StreamRepositoryImpl
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository
import io.reactivex.Single

/**
 * Used to search for subscribed streams by query.
 * Separated from GetSubscribedStreamsUseCase as future search strategies may differ
 * from simply requesting all streams
 */
interface SearchSubscribeStreamsUseCase : (String) -> Single<List<StreamData>> {
    override fun invoke(searchQuery: String): Single<List<StreamData>>
}

class SearchSubscribeStreamsUseCaseImpl : SearchSubscribeStreamsUseCase {

    private val streamRepository: StreamRepository = StreamRepositoryImpl()

    override fun invoke(searchQuery: String): Single<List<StreamData>> {
        return streamRepository.loadSubscribedStreams()
            .map { streams ->
                streams.filter {
                    it.streamName.contains(searchQuery, ignoreCase = true)
                }
            }
    }
}
