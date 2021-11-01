package com.homework.coursework.domain.usecase

import com.homework.coursework.data.StreamRepositoryImpl
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository
import io.reactivex.Observable

interface SearchAllStreamsUseCase : (String) -> Observable<List<StreamData>> {
    override fun invoke(searchQuery: String): Observable<List<StreamData>>
}

class SearchAllStreamsUseCaseImpl : SearchAllStreamsUseCase {

    private val streamRepository: StreamRepository = StreamRepositoryImpl()

    override fun invoke(searchQuery: String): Observable<List<StreamData>> {
        return streamRepository.loadStreams()
            .map { streams ->
                streams.filter {
                    it.streamName.contains(searchQuery, ignoreCase = true)
                }
            }
    }
}
