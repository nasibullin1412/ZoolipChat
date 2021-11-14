package com.homework.coursework.data.frameworks.network.mappersimpl

import com.homework.coursework.data.dto.StreamWithTopics
import com.homework.coursework.data.mappers.StreamMapper
import com.homework.coursework.domain.entity.StreamData
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class StreamDtoMapper : StreamMapper<List<StreamWithTopics>> {

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
