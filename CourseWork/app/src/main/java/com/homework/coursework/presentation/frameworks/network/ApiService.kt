package com.homework.coursework.presentation.frameworks.network

import com.homework.coursework.data.dto.DtoResponse
import com.homework.coursework.data.dto.StreamDto
import com.homework.coursework.presentation.utils.NetworkConstants.BASE_URL
import com.homework.coursework.presentation.utils.addJsonConverter
import com.homework.coursework.presentation.utils.setClient
import io.reactivex.Observable
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET

@ExperimentalSerializationApi
interface ApiService {

    @GET("streams")
    fun getAllStreams(): Observable<DtoResponse<StreamDto>>

    @GET("users/me/subscriptions")
    fun getSubscribedStreams(): Observable<DtoResponse<StreamDto>>

    companion object {
        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .setClient()
                .addJsonConverter()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
