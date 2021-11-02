package com.homework.coursework.domain.usecase

import com.homework.coursework.data.StreamRepositoryImpl
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository
import io.reactivex.Observable

/**
 * Used when user want see all streams without any action with search
 */
interface GetSubscribedStreamsUseCase : () -> Observable<List<StreamData>> {
    override fun invoke(): Observable<List<StreamData>>
}

class GetSubscribedStreamsUseCaseImpl : GetSubscribedStreamsUseCase {

    private val streamRepository: StreamRepository = StreamRepositoryImpl()

    override fun invoke(): Observable<List<StreamData>> {
        return streamRepository.loadSubscribedStreams()
    }
}
