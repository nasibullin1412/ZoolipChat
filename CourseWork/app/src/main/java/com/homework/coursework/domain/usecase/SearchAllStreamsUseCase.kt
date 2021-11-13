package com.homework.coursework.domain.usecase

import com.homework.coursework.data.StreamRepositoryImpl
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository
import io.reactivex.Single

/**
 * Used to search for all streams by query.
 * Separated from GetAllStreamsUseCase as future search strategies may differ
 * from simply requesting all streams
 */
interface SearchAllStreamsUseCase : (String) -> Single<List<StreamData>> {
    override fun invoke(searchQuery: String): Single<List<StreamData>>
}

class SearchAllStreamsUseCaseImpl : SearchAllStreamsUseCase {

    private val streamRepository: StreamRepository = StreamRepositoryImpl()

    override fun invoke(searchQuery: String): Single<List<StreamData>> {
        return streamRepository.loadStreams()
            .map { streams ->
                streams.filter {
                    it.streamName.contains(searchQuery, ignoreCase = true)
                }
            }
    }
}
