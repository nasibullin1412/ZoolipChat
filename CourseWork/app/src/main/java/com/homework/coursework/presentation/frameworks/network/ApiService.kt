package com.homework.coursework.presentation.frameworks.network

import com.homework.coursework.data.dto.MessagesResponse
import com.homework.coursework.data.dto.StreamsResponse
import com.homework.coursework.data.dto.TopicsResponse
import com.homework.coursework.presentation.frameworks.network.utils.NetworkConstants.BASE_URL
import com.homework.coursework.presentation.frameworks.network.utils.addJsonConverter
import com.homework.coursework.presentation.frameworks.network.utils.setClient
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.*

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

    @POST("messages/{message_id}/reactions")
    fun addReaction(
        @Path("message_id") messageId: Int,
        @Query("emoji_name") emojiName: String,
        @Query("emoji_code") emojiCode: String,
        @Query("reaction_type") reactionType: String
    ): Completable

    @DELETE("messages/{message_id}/reactions")
    fun deleteReaction(
        @Path("message_id") messageId: Int,
        @Query("emoji_name") emojiName: String,
        @Query("emoji_code") emojiCode: String,
        @Query("reaction_type") reactionType: String
    ): Completable

    @POST("messages")
    fun addMessage(
        @Query("type") type: String,
        @Query("to") to: String,
        @Query("content") content: String,
        @Query("topic") topic: String
    ): Completable

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
