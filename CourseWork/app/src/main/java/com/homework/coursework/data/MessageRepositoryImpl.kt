package com.homework.coursework.data

import android.util.Log
import androidx.room.EmptyResultSetException
import com.homework.coursework.data.frameworks.database.crossref.MessageToUserCrossRef
import com.homework.coursework.data.frameworks.database.dao.MessageDao
import com.homework.coursework.data.frameworks.database.dao.MessageToUserCrossRefDao
import com.homework.coursework.data.frameworks.database.dao.UserDao
import com.homework.coursework.data.frameworks.database.entities.MessageWithEmojiEntity
import com.homework.coursework.data.frameworks.database.mappersimpl.MessageDataMapper
import com.homework.coursework.data.frameworks.database.mappersimpl.MessageEntityMapper
import com.homework.coursework.data.frameworks.database.mappersimpl.UserDataListMapper
import com.homework.coursework.data.frameworks.network.ApiService
import com.homework.coursework.data.frameworks.network.mappersimpl.MessageDtoMapper
import com.homework.coursework.data.frameworks.network.utils.MessageQuery
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.BASE_URL
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.FILE
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.NUM_AFTER
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.NUM_BEFORE
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.MessageRepository
import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.InputStream
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val _apiService: Lazy<ApiService>,
    private val messageDao: MessageDao,
    private val messageToUserCrossRefDao: MessageToUserCrossRefDao,
    private val userDao: UserDao
) : MessageRepository {

    @ExperimentalSerializationApi
    @Inject
    internal lateinit var messageDtoMapper: MessageDtoMapper

    @Inject
    internal lateinit var messageDataMapper: MessageDataMapper

    @Inject
    internal lateinit var compositeDisposable: CompositeDisposable

    @Inject
    internal lateinit var userDataListMapper: UserDataListMapper

    @Inject
    internal lateinit var messageEntityMapper: MessageEntityMapper

    @Inject
    internal lateinit var messageQuery: MessageQuery

    private val apiService: ApiService get() = _apiService.get()

    @ExperimentalSerializationApi
    override fun initMessages(
        streamData: StreamData,
        topicData: TopicData,
        currUserId: Int
    ): Observable<List<MessageData>> {
        return Observable.concatArrayEagerDelayError(
            getLocalMessage(streamData, topicData),
            getRemoteMessage(streamData, topicData, currUserId)
        )
    }

    @ExperimentalSerializationApi
    override fun loadMessages(
        streamData: StreamData,
        topicData: TopicData,
        currUserId: Int
    ): Observable<List<MessageData>> {
        return loadOrUpdateMessage(streamData, topicData, currUserId, NUM_BEFORE)
    }

    @ExperimentalSerializationApi
    override fun updateMessage(
        streamData: StreamData,
        topicData: TopicData,
        currUserId: Int
    ): Observable<List<MessageData>> {
        return loadOrUpdateMessage(streamData, topicData, currUserId, NUM_AFTER)
    }

    @ExperimentalSerializationApi
    override fun addMessages(
        streamData: StreamData,
        topicData: TopicData,
        content: String
    ): Observable<Int> {
        return apiService.addMessage(
            queryMap = messageQuery.addMessage(
                streamData = streamData,
                topicData = topicData,
                content = content
            )
        ).map { it.data }
    }

    @ExperimentalSerializationApi
    override fun deleteMessage(messageData: MessageData): Completable {
        return apiService.deleteMessage(messageData.messageId)
    }

    override fun saveMessages(
        streamData: StreamData,
        topicData: TopicData,
        messages: List<MessageData>,
        currUserId: Int
    ): Completable {
        return Completable.fromCallable {
            storeUsersInDb(
                messageDataList = messages,
                topicData = topicData,
                streamData = streamData,
                currUserId = currUserId
            )
        }
    }

    override fun editMessage(messageData: MessageData): Completable {
        return apiService.editMessage(
            messageId = messageData.messageId,
            queryMap = messageQuery.editMessage(messageData = messageData)
        )
    }

    @ExperimentalSerializationApi
    override fun addFile(
        streamData: StreamData,
        topicData: TopicData,
        inputStream: InputStream,
        file: String
    ): Observable<Int> {
        val bytes = inputStream.readBytes()
        val fileName = file.dropWhile { it != '/' }.drop(1)
        val requestBody: RequestBody = RequestBody.create("*/*".toMediaTypeOrNull(), bytes)
        val fileToUpload = MultipartBody.Part.createFormData(FILE, fileName, requestBody)
        return apiService.addFile(fileToUpload).flatMap {
            val content = "[$fileName]($BASE_URL${it.data})"
            addMessages(
                streamData = streamData,
                topicData = topicData,
                content = content
            )
        }
    }

    @ExperimentalSerializationApi
    private fun getRemoteMessage(
        streamData: StreamData,
        topicData: TopicData,
        currUserId: Int
    ): Observable<List<MessageData>> {
        return apiService.loadMessages(
            queryMap = messageQuery.getFirstMessages(
                streamData = streamData,
                topicData = topicData
            )
        ).map { messageDtoMapper(messages = it, currUserId = currUserId) }
            .onErrorReturn { error: Throwable ->
                listOf(
                    MessageData.getErrorObject(error)
                )
            }
    }

    @ExperimentalSerializationApi
    private fun loadOrUpdateMessage(
        streamData: StreamData,
        topicData: TopicData,
        currUserId: Int,
        numBefore: Int
    ): Observable<List<MessageData>> {
        return apiService.loadMessages(
            queryMap = messageQuery.loadMoreMessages(
                streamData = streamData,
                topicData = topicData,
                numBefore = numBefore
            )
        ).map { messageDtoMapper(messages = it, currUserId = currUserId) }
    }

    private fun storeUsersInDb(
        messageDataList: List<MessageData>,
        streamData: StreamData,
        topicData: TopicData,
        currUserId: Int
    ) {
        val length = messageDao.getMessageNumber(streamData.id, topicData.topicName)
        if (length >= DATABASE_MESSAGE_THRESHOLD) {
            return
        }
        val newLength = messageDataList.size + length
        val dropNumber = positiveOrZero(value = newLength - DATABASE_MESSAGE_THRESHOLD)
        val messageListAfterDrop = messageDataList.drop(dropNumber)
        val userMap = messageListAfterDrop.groupBy { it.userData }
        Observable.fromCallable {
            userDao.insertAll(userDataListMapper(userMap.keys.toList()))
        }.subscribeOn(Schedulers.io())
            .switchMap {
                storeMessagesInDb(
                    messageDataMapper(
                        messageDataList = messageListAfterDrop,
                        topicName = topicData.topicName,
                        streamId = streamData.id,
                        currUserId = currUserId
                    )
                )
            }
            .subscribeBy(
                onNext = { createMessageToUserCrossRef(messageListAfterDrop) },
                onError = { Log.e("FromRoom", it.message.toString()) }
            )
            .addTo(compositeDisposable)
    }

    private fun createMessageToUserCrossRef(messageDataList: List<MessageData>) {
        val messageToUserCrossRefList = ArrayList<MessageToUserCrossRef>()
        messageDataList.forEach {
            messageToUserCrossRefList.add(
                MessageToUserCrossRef(
                    id = 0,
                    messageId = it.messageId,
                    userId = it.userData.id
                )
            )
        }
        messageToUserCrossRefList.forEach {
            messageToUserCrossRefDao.insert(it)
        }
    }

    private fun storeMessagesInDb(
        messageWithEmojiEntity: List<MessageWithEmojiEntity>
    ): Observable<Completable> {
        return Observable.fromCallable {
            messageDao.insertMessages(messageWithEmojiEntity)
                .doOnError {
                    Log.d("FromRoom", it.message.toString())
                }
        }
    }

    private fun getLocalMessage(
        streamData: StreamData,
        topicData: TopicData
    ): Observable<List<MessageData>> {
        return getNeedDao(streamId = streamData.id, topic = topicData)
            .map { if (it.isEmpty()) throw EmptyResultSetException("empty db") else it }
            .map(messageEntityMapper)
            .toObservable()
            .onErrorReturn { error: Throwable ->
                listOf(
                    MessageData.getErrorObject(error)
                )
            }
    }

    private fun getNeedDao(streamId: Int, topic: TopicData) =
        if (topic.numberOfMess != UNKNOWN_TOPIC_ID) {
            messageDao.getTopicMessages(streamId = streamId, topicName = topic.topicName)
        } else {
            messageDao.getStreamMessage(streamId = streamId)
        }

    private fun positiveOrZero(value: Int) = if (value > 0) {
        value
    } else {
        0
    }

    companion object {
        private const val UNKNOWN_TOPIC_ID = -1
        const val DATABASE_MESSAGE_THRESHOLD = 50
    }
}
