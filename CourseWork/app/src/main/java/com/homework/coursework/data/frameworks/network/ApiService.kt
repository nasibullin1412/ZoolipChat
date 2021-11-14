package com.homework.coursework.data.frameworks.network

import com.homework.coursework.data.dto.*
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.BASE_URL
import com.homework.coursework.data.frameworks.network.utils.addJsonConverter
import com.homework.coursework.data.frameworks.network.utils.setClient
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.*

@ExperimentalSerializationApi
interface ApiService {

    /**
     * Get all organization streams
     * @return Observable of streams
     */
    @GET("streams")
    fun getAllStreams(): Observable<StreamsResponse>

    /**
     * Get subscribed streams
     * @return Observable of streams
     */
    @GET("users/me/subscriptions")
    fun getSubscribedStreams(): Observable<StreamsResponse>

    /**
     * Get topics of selected stream
     * @param streamId id of selected stream
     */
    @GET("users/me/{stream_id}/topics")
    fun getTopics(@Path("stream_id") streamId: Int): Observable<TopicsResponse>

    /**
     * get messages of selected topic
     * @param anchor supports special string values for when the client wants the server to
     * compute the anchor to use
     * @param numBefore the number of messages with IDs less than the anchor to retrieve.
     * @param numAfter the number of messages with IDs greater than the anchor to retrieve.
     */
    @GET("messages")
    fun getMessages(
        @Query("anchor") anchor: String,
        @Query("num_after") numAfter: Int,
        @Query("num_before") numBefore: Int,
        @Query("narrow") narrow: String,
    ): Observable<MessagesResponse>

    /**
     * Add emoji to message
     * @param messageId the target message's ID.
     * @param emojiName target emoji's human-readable name
     * @param emojiCode a unique identifier, defining the specific emoji codepoint requested
     * @param reactionType type of emoji unique identifier
     */
    @POST("messages/{message_id}/reactions")
    fun addReaction(
        @Path("message_id") messageId: Int,
        @Query("emoji_name") emojiName: String,
        @Query("emoji_code") emojiCode: String,
        @Query("reaction_type") reactionType: String
    ): Completable

    /**
     * Delete emoji from message
     * @param messageId the target message's ID.
     * @param emojiName target emoji's human-readable name
     * @param emojiCode a unique identifier, defining the specific emoji codepoint requested
     * @param reactionType type of emoji unique identifier
     */
    @DELETE("messages/{message_id}/reactions")
    fun deleteReaction(
        @Path("message_id") messageId: Int,
        @Query("emoji_name") emojiName: String,
        @Query("emoji_code") emojiCode: String,
        @Query("reaction_type") reactionType: String
    ): Completable

    /**
     * Add new message to topic
     * @param type of message to be sent. private for a private message and stream
     * for a stream message.
     * @param to for stream messages, either the name or integer ID of the stream.
     * @param content the content of the message
     */
    @POST("messages")
    fun addMessage(
        @Query("type") type: String,
        @Query("to") to: String,
        @Query("content") content: String,
        @Query("topic") topic: String
    ): Completable

    /**
     * Get actual user data
     * @return Observable of user data
     */
    @GET("users/me")
    fun getMe(): Observable<UserDto>

    /**
     * Get status of user
     * @param userId id of user, which status need to be known
     */
    @GET("users/{user_id}/presence")
    fun getStatus(@Path("user_id") userId: Int): Observable<StatusResponse>

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
