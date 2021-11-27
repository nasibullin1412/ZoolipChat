package com.homework.coursework.domain.usecase

import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Used to search for all streams by query.
 * Separated from GetAllStreamsUseCase as future search strategies may differ
 * from simply requesting all streams
 */
interface SearchAllStreamsUseCase : (String) -> Observable<List<StreamData>> {
    override fun invoke(searchQuery: String): Observable<List<StreamData>>
}

class SearchAllStreamsUseCaseImpl @Inject constructor(
    private val streamRepository: StreamRepository
) : SearchAllStreamsUseCase {

    override fun invoke(searchQuery: String): Observable<List<StreamData>> {
        return streamRepository.loadAllStreams()
            .map { streams ->
                if (streams.first().errorHandle.isError) {
                    return@map streams
                }
                streams.filter {
                    it.streamName.contains(searchQuery, ignoreCase = true)
                }
            }
    }
}
