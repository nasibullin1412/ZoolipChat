package com.homework.coursework.data.frameworks.network.mappersimpl

import com.homework.coursework.data.dto.TopicDto
import com.homework.coursework.domain.entity.TopicData
import dagger.Reusable
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@Reusable
@ExperimentalSerializationApi
class TopicDtoMapper @Inject constructor() : (List<TopicDto>) -> (List<TopicData>) {
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
