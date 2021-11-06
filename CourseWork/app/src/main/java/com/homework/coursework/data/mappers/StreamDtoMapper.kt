package com.homework.coursework.data.mappers

import com.homework.coursework.data.dto.DtoResponse
import com.homework.coursework.data.dto.StreamDto
import com.homework.coursework.domain.entity.StreamData
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class StreamDtoMapper : (DtoResponse<StreamDto>) -> (List<StreamData>) {
    override fun invoke(streamsDto: DtoResponse<StreamDto>): List<StreamData> {
        return streamsDto.data?.map { streamDto ->
            with(streamDto) {
                StreamData(
                    id = streamId,
                    streamName = name,
                    description = description,
                    dateCreated = dateCreated
                )
            }
        } ?: throw IllegalArgumentException("data required")
    }
}