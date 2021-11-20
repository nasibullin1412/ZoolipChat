package com.homework.coursework.domain.usecase

import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository
import io.reactivex.Observable

/**
 * Used when user want get all streams without any action with search
 */
interface GetAllStreamsUseCase : () -> Observable<List<StreamData>> {
    override fun invoke(): Observable<List<StreamData>>
}

class GetAllStreamsUseCaseImpl(
    private val streamRepository: StreamRepository
) : GetAllStreamsUseCase {

    override fun invoke(): Observable<List<StreamData>> {
        return streamRepository.loadAllStreams()
    }
}
