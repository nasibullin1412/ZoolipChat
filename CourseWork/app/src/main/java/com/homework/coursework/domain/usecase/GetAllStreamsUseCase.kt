package com.homework.coursework.domain.usecase

import com.homework.coursework.data.StreamRepositoryImpl
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository

interface GetAllStreamsUseCase : () -> List<StreamData> {
    override fun invoke(): List<StreamData>
}

class GetAllStreamsUseCaseImpl : GetAllStreamsUseCase {

    private val streamRepository: StreamRepository = StreamRepositoryImpl()

    override fun invoke(): List<StreamData> {
        return streamRepository.loadStreams()
    }
}
