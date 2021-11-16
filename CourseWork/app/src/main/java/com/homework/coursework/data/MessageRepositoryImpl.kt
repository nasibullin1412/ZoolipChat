package com.homework.coursework.data

import android.util.Log
import com.homework.coursework.data.dto.MessagesResponse
import com.homework.coursework.data.dto.Narrow
import com.homework.coursework.data.frameworks.database.AppDatabase
import com.homework.coursework.data.frameworks.database.crossref.MessageToUserCrossRef
import com.homework.coursework.data.frameworks.database.entities.MessageWithEmojiEntity
import com.homework.coursework.data.frameworks.database.mappersimpl.MessageDataMapper
import com.homework.coursework.data.frameworks.database.mappersimpl.UserDataListMapper
import com.homework.coursework.data.frameworks.network.mappersimpl.MessageDtoMapper
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.NUM_AFTER
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.NUM_BEFORE
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.STREAM
import com.homework.coursework.data.mappers.MessageMapper
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.MessageRepository
import com.homework.coursework.presentation.App
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MessageRepositoryImpl : MessageRepository {

    private val messageDtoMapper: MessageMapper<MessagesResponse> = MessageDtoMapper()
    private val messageDataMapper: MessageDataMapper = MessageDataMapper()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val userDataListMapper: UserDataListMapper = UserDataListMapper()


    override fun loadMessages(
        streamData: StreamData,
        topicData: TopicData
    ): Observable<List<MessageData>> {
        return getRemoteMessage(streamData, topicData)
    }

    override fun addMessages(
        streamData: StreamData,
        topicData: TopicData,
        content: String
    ): Observable<Int> {
        return App.instance.apiService.addMessage(
            type = STREAM,
            to = streamData.streamName,
            content = content,
            topic = topicData.topicName
        ).map { it.data }
    }

    private fun getRemoteMessage(
        streamData: StreamData,
        topicData: TopicData
    ): Observable<List<MessageData>> {
        val narrow = Narrow.createNarrowForMessage(streamData, topicData)
        return App.instance.apiService.getMessages(
            anchor = topicData.numberOfMess,
            numAfter = NUM_AFTER,
            numBefore = NUM_BEFORE,
            narrow = narrow
        ).map(messageDtoMapper)
            .doOnNext {
                storeUsersInDb(
                    messageDataList = it,
                    topicData = topicData,
                    streamData = streamData
                )
            }
    }

    private fun storeUsersInDb(
        messageDataList: List<MessageData>,
        streamData: StreamData,
        topicData: TopicData
    ) {
        val length =
            AppDatabase.instance.messageDao().getMessageNumber(streamData.id, topicData.topicName)
        if (length >= DATABASE_MESSAGE_THRESHOLD) {
            return
        }
        val newLength = messageDataList.size + length
        val dropNumber = positiveOrZero(value = newLength - DATABASE_MESSAGE_THRESHOLD)
        val messageListAfterDrop = messageDataList.drop(dropNumber)
        val userMap = messageListAfterDrop.groupBy { it.userData }
        Observable.fromCallable {
            AppDatabase.instance.userDao().insertAll(
                userDataListMapper(userMap.keys.toList())
            )
        }.subscribeOn(Schedulers.io())
            .switchMap {
                storeMessagesInDb(
                    messageDataMapper(
                        messageDataList = messageListAfterDrop,
                        topicName = topicData.topicName,
                        streamId = streamData.id
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
            AppDatabase.instance.messageToUserCrossRefDao().insert(it)
        }
    }

    private fun storeMessagesInDb(messageWithEmojiEntity: List<MessageWithEmojiEntity>): Observable<Completable>? {
        return Observable.fromCallable {
            AppDatabase.instance.messageDao().insertMessages(messageWithEmojiEntity)
                .doOnError {
                    Log.d("FromRoom", it.message.toString())
                }
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
