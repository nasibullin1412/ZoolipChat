package com.homework.coursework.data

import com.homework.coursework.data.frameworks.network.ApiService
import com.homework.coursework.data.frameworks.network.utils.StreamQuery
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

    @Inject
    internal lateinit var streamQueryMap: StreamQuery

    @ExperimentalSerializationApi
    override fun subscribeStream(subscribeData: SubscribeData): Observable<String> {
        return subscribeData.run {
            apiService.createStream(
                queryMap = streamQueryMap.createStream(subscribeData = subscribeData)
            ).map { it.result }
        }
    }
}
