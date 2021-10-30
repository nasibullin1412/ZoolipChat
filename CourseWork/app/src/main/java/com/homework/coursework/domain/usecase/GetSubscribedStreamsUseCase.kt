package com.homework.coursework.domain.usecase

import com.homework.coursework.data.StreamRepositoryImpl
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository

interface GetSubscribedStreamsUseCase : () -> List<StreamData> {
    override fun invoke(): List<StreamData>
}

class GetSubscribedStreamsUseCaseImpl : GetSubscribedStreamsUseCase {

    private val streamRepository: StreamRepository = StreamRepositoryImpl()

    override fun invoke(): List<StreamData> {
        return streamRepository.loadSubscribedStreams()
    }
}
