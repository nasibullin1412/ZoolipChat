package com.homework.coursework.data

import android.util.Log
import androidx.room.EmptyResultSetException
import com.homework.coursework.data.dto.Narrow
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
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.NEWEST
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.NUM_AFTER
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.NUM_BEFORE
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.STREAM
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
    override fun loadOrUpdateMessages(
        streamData: StreamData,
        topicData: TopicData,
        currUserId: Int,
        numBefore: Int
    ): Observable<List<MessageData>> {
        return loadOrUpdateMessage(streamData, topicData, currUserId, numBefore)
    }

    @ExperimentalSerializationApi
    override fun addMessages(
        streamData: StreamData,
        topicData: TopicData,
        content: String
    ): Observable<Int> {
        return apiService.addMessage(
            type = STREAM,
            to = streamData.streamName,
            content = content,
            topic = topicData.topicName
        ).map { it.data }
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

    @ExperimentalSerializationApi
    private fun getRemoteMessage(
        streamData: StreamData,
        topicData: TopicData,
        currUserId: Int
    ): Observable<List<MessageData>> {
        val narrow = Narrow.createNarrowForMessage(streamData, topicData)
        return apiService.getFirstMessages(
            anchor = NEWEST,
            numAfter = NUM_AFTER,
            numBefore = NUM_BEFORE,
            narrow = narrow
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
        val narrow = Narrow.createNarrowForMessage(streamData, topicData)
        return apiService.loadMoreMessages(
            anchor = topicData.numberOfMess,
            numAfter = NUM_AFTER,
            numBefore = numBefore,
            narrow = narrow
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
        return messageDao
            .getMessages(streamId = streamData.id, topicName = topicData.topicName)
            .map { if (it.isEmpty()) throw EmptyResultSetException("empty db") else it }
            .map(messageEntityMapper)
            .toObservable()
            .onErrorReturn { error: Throwable ->
                listOf(
                    MessageData.getErrorObject(error)
                )
            }
    }

    private fun positiveOrZero(value: Int) = if (value > 0) {
        value
    } else {
        0
    }

    companion object {
        const val DATABASE_MESSAGE_THRESHOLD = 50
    }
}
