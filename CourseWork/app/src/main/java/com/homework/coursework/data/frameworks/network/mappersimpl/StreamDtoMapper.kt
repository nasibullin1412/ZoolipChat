package com.homework.coursework.data.frameworks.network.mappersimpl

import com.homework.coursework.data.frameworks.network.dto.StreamWithTopics
import com.homework.coursework.domain.entity.StreamData
import dagger.Reusable
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@Reusable
@ExperimentalSerializationApi
class StreamDtoMapper @Inject constructor() : (List<StreamWithTopics>) -> (List<StreamData>) {

    @Inject
    internal lateinit var topicDtoMapper: TopicDtoMapper

    override fun invoke(streams: List<StreamWithTopics>): List<StreamData> {
        return streams.map { streamPair ->
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
