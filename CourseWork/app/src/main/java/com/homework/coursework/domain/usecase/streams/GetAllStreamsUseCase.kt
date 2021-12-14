package com.homework.coursework.domain.usecase.streams

import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Used when user want get all streams without any action with search
 */
interface GetAllStreamsUseCase : () -> Observable<List<StreamData>> {
    override fun invoke(): Observable<List<StreamData>>
}

class GetAllStreamsUseCaseImpl @Inject constructor(
    private val streamRepository: StreamRepository
) : GetAllStreamsUseCase {

    override fun invoke(): Observable<List<StreamData>> {
        return streamRepository.loadAllStreams()
    }
}
