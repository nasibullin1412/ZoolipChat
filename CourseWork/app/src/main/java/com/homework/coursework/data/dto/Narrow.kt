package com.homework.coursework.data.dto

import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Narrow(
    val operand: String,
    val operator: String
) {
    companion object {
        fun createNarrowForMessage(streamData: StreamData, topicData: TopicData): String {
            return Json.encodeToString(
                listOf(
                    Narrow(
                        operator = "stream",
                        operand = streamData.streamName
                    ),
                    Narrow(
                        operator = "topic",
                        operand = topicData.topicName
                    )
                )
            )
        }
    }
}
