package com.homework.coursework.domain.usecase

import com.homework.coursework.data.StreamRepositoryImpl
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository
import io.reactivex.Observable

/**
 * Used when user want get all streams without any action with search
 */
interface GetAllStreamsUseCase : () -> Observable<List<StreamData>> {
    override fun invoke(): Observable<List<StreamData>>
}

class GetAllStreamsUseCaseImpl : GetAllStreamsUseCase {

    private val streamRepository: StreamRepository = StreamRepositoryImpl()

    override fun invoke(): Observable<List<StreamData>> {
        return streamRepository.loadAllStreams()
    }
}
