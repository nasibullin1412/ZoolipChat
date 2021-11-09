package com.homework.coursework.data

import com.homework.coursework.data.dto.Narrow
import com.homework.coursework.data.mappers.MessageDtoMapper
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.MessageRepository
import com.homework.coursework.presentation.App
import com.homework.coursework.presentation.frameworks.network.utils.NetworkConstants.ANCHOR
import com.homework.coursework.presentation.frameworks.network.utils.NetworkConstants.NUM_AFTER
import com.homework.coursework.presentation.frameworks.network.utils.NetworkConstants.NUM_BEFORE
import com.homework.coursework.presentation.frameworks.network.utils.NetworkConstants.STREAM
import io.reactivex.Completable
import io.reactivex.Observable
import kotlinx.serialization.ExperimentalSerializationApi


@ExperimentalSerializationApi
class MessageRepositoryImpl : MessageRepository {

    private val messageDtoMapper: MessageDtoMapper = MessageDtoMapper()

    override fun loadMessages(
        streamData: StreamData,
        topicData: TopicData
    ): Observable<List<MessageData>> {
        val narrow = Narrow.createNarrowForMessage(streamData, topicData)
        return App.instance.apiService.getMessages(
            anchor = ANCHOR,
            numAfter = NUM_AFTER,
            numBefore = NUM_BEFORE,
            narrow = narrow
        ).map(messageDtoMapper)
    }

    override fun addMessages(
        streamData: StreamData,
        topicData: TopicData,
        content: String
    ): Completable {
        return App.instance.apiService.addMessage(
            type = STREAM,
            to = streamData.streamName,
            content = content,
            topic = topicData.topicName
        )
    }
}
