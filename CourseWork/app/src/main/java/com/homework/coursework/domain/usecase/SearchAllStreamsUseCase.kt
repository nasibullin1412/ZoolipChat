package com.homework.coursework.domain.usecase

import com.homework.coursework.data.StreamRepositoryImpl
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository
import io.reactivex.Observable

/**
 * Used to search for all streams by query.
 * Separated from GetAllStreamsUseCase as future search strategies may differ
 * from simply requesting all streams
 */
interface SearchAllStreamsUseCase : (String) -> Observable<List<StreamData>> {
    override fun invoke(searchQuery: String): Observable<List<StreamData>>
}

class SearchAllStreamsUseCaseImpl : SearchAllStreamsUseCase {

    private val streamRepository: StreamRepository = StreamRepositoryImpl()

    override fun invoke(searchQuery: String): Observable<List<StreamData>> {
        return streamRepository.loadAllStreams()
            .map { streams ->
                if (streams.first().errorHandle.isError){return@map streams}
                streams.filter {
                    it.streamName.contains(searchQuery, ignoreCase = true)
                }
            }
    }
}
