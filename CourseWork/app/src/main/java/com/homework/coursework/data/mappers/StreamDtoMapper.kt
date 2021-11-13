package com.homework.coursework.data.mappers

import com.homework.coursework.data.dto.StreamWithTopics
import com.homework.coursework.domain.entity.StreamData
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class StreamDtoMapper : (List<StreamWithTopics>) -> (List<StreamData>) {

    private val topicDtoMapper: TopicDtoMapper = TopicDtoMapper()

    override fun invoke(streamsWithtopics: List<StreamWithTopics>): List<StreamData> {
        return streamsWithtopics.map { streamPair ->
            with(streamPair) {
                StreamData(
                    id = first.streamId,
                    streamName = first.name,
                    description = first.description,
                    dateCreated = first.dateCreated,
                    topics = topicDtoMapper(second)
                )
            }
        }
    }
}
