package com.homework.coursework.domain.usecase

import com.homework.coursework.data.StreamRepositoryImpl
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository
import io.reactivex.Single

/**
 * Used when user want get all streams without any action with search
 */
interface GetAllStreamsUseCase : () -> Single<List<StreamData>> {
    override fun invoke(): Single<List<StreamData>>
}

class GetAllStreamsUseCaseImpl : GetAllStreamsUseCase {

    private val streamRepository: StreamRepository = StreamRepositoryImpl()

    override fun invoke(): Single<List<StreamData>> {
        return streamRepository.loadStreams()
    }
}
