package com.homework.coursework.data

import com.homework.coursework.data.dto.CreateDto
import com.homework.coursework.data.frameworks.network.ApiService
import com.homework.coursework.domain.entity.SubscribeData
import com.homework.coursework.domain.repository.SubscribeStreamRepository
import dagger.Lazy
import io.reactivex.Observable
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

class SubscribeStreamRepositoryImpl @Inject constructor(
    private val _apiService: Lazy<ApiService>
) : SubscribeStreamRepository {

    private val apiService: ApiService get() = _apiService.get()

    @ExperimentalSerializationApi
    override fun subscribeStream(subscribeData: SubscribeData): Observable<String> {
        val createDto = CreateDto.createNarrowForMessage(subscribeData = subscribeData)
        return subscribeData.run {
            apiService.createStream(
                create = createDto,
                inviteOnly = inviteOnly,
                isWebPublic = isWebPublic,
                historyPublicToSubscribers = historyPublicToSubscribers
            )
        }.map { it.result }
    }
}
