package com.homework.coursework.presentation.frameworks.network

import android.net.Uri
import com.homework.coursework.data.dto.*
import com.homework.coursework.presentation.frameworks.network.utils.NetworkConstants.BASE_URL
import com.homework.coursework.presentation.frameworks.network.utils.addJsonConverter
import com.homework.coursework.presentation.frameworks.network.utils.setClient
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonElement
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import java.net.URL

@ExperimentalSerializationApi
interface ApiService {

    @GET("streams")
    fun getAllStreams(): Observable<StreamsResponse>

    @GET("users/me/subscriptions")
    fun getSubscribedStreams(): Observable<StreamsResponse>

    @GET("users/me/{stream_id}/topics")
    fun getTopics(@Path("stream_id") streamId: Int): Observable<TopicsResponse>

    @GET("messages")
    fun getMessages(
        @Query("anchor") anchor: String,
        @Query("num_after") numAfter: Int,
        @Query("num_before") numBefore: Int,
        @Query("narrow") narrow: String,
    ): Observable<MessagesResponse>

    companion object {
        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .setClient()
                .addJsonConverter()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(ApiService::class.java)
        }
    }
}
