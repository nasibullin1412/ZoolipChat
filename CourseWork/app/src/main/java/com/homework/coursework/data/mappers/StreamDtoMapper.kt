package com.homework.coursework.data.mappers

import com.homework.coursework.data.dto.StreamsResponse
import com.homework.coursework.domain.entity.StreamData
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class StreamDtoMapper : (StreamsResponse) -> (List<StreamData>) {
    override fun invoke(streamsDto: StreamsResponse): List<StreamData> {
        return streamsDto.data?.map { streamDto ->
            with(streamDto) {
                StreamData(
                    id = streamId,
                    streamName = name,
                    description = description,
                    dateCreated = dateCreated
                )
            }
        } ?: throw IllegalArgumentException(streamsDto.msg)
    }
}
