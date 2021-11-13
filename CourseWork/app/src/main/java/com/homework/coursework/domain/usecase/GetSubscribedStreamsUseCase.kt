package com.homework.coursework.domain.usecase

import com.homework.coursework.data.StreamRepositoryImpl
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository
import io.reactivex.Single

/**
 * Used when user want see all streams without any action with search
 */
interface GetSubscribedStreamsUseCase : () -> Single<List<StreamData>> {
    override fun invoke(): Single<List<StreamData>>
}

class GetSubscribedStreamsUseCaseImpl : GetSubscribedStreamsUseCase {

    private val streamRepository: StreamRepository = StreamRepositoryImpl()

    override fun invoke(): Single<List<StreamData>> {
        return streamRepository.loadSubscribedStreams()
    }
}
