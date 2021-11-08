package com.homework.coursework.data.mappers

import com.homework.coursework.data.dto.TopicsResponse
import com.homework.coursework.domain.entity.TopicData
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class TopicDtoMapper : (TopicsResponse) -> (List<TopicData>) {
    override fun invoke(topicResponse: TopicsResponse): List<TopicData> {
        return topicResponse.data?.mapIndexed { index, topicDto ->
            with(topicDto) {
                TopicData(
                    id = index,
                    topicName = name,
                    numberOfMess = maxId
                )
            }
        } ?: throw IllegalArgumentException("data required")
    }
}