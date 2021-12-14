package com.homework.coursework.domain.usecase.streams

import com.homework.coursework.domain.entity.SubscribeData
import com.homework.coursework.domain.repository.SubscribeStreamRepository
import io.reactivex.Observable
import javax.inject.Inject

interface SubscribeToStreamUseCase : (SubscribeData) -> Observable<String> {
    override fun invoke(subscribeData: SubscribeData): Observable<String>
}

class SubscribeToStreamUseCaseImpl @Inject constructor(
    private val subscribeStreamRepository: SubscribeStreamRepository
) : SubscribeToStreamUseCase {

    override fun invoke(subscribeData: SubscribeData): Observable<String> {
        return subscribeStreamRepository.subscribeStream(subscribeData = subscribeData)
    }
}
