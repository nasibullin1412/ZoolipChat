package com.homework.coursework.data.mappers

import com.homework.coursework.data.dto.TopicDto
import com.homework.coursework.data.dto.TopicsResponse
import com.homework.coursework.domain.entity.TopicData
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class TopicDtoMapper : (List<TopicDto>) -> (List<TopicData>) {
    override fun invoke(topics: List<TopicDto>): List<TopicData> {
        return topics.mapIndexed { index, topicDto ->
            with(topicDto) {
                TopicData(
                    id = index,
                    topicName = name,
                    numberOfMess = maxId
                )
            }
        }
    }
}
