package com.homework.coursework.domain.usecase

import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository
import io.reactivex.Observable

/**
 * Used to search for subscribed streams by query.
 * Separated from GetSubscribedStreamsUseCase as future search strategies may differ
 * from simply requesting all streams
 */
interface SearchSubscribeStreamsUseCase : (String) -> Observable<List<StreamData>> {
    override fun invoke(searchQuery: String): Observable<List<StreamData>>
}

class SearchSubscribeStreamsUseCaseImpl(
    private val streamRepository: StreamRepository
) : SearchSubscribeStreamsUseCase {

    override fun invoke(searchQuery: String): Observable<List<StreamData>> {
        return streamRepository.loadSubscribedStreams()
            .map { streams ->
                if (streams.isEmpty() || streams.first().errorHandle.isError) {
                    return@map streams
                }
                streams.filter {
                    it.streamName.contains(searchQuery, ignoreCase = true)
                }
            }
    }
}
