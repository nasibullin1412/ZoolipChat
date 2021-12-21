package com.homework.coursework.data.frameworks.network.dto

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
        private const val UNKNOWN_TOPIC_ID = -1

        fun createNarrowForMessage(streamData: StreamData, topicData: TopicData): String {
            val narrow: ArrayList<Narrow> = arrayListOf(
                Narrow(
                    operator = "stream",
                    operand = streamData.streamName
                )
            )
            if (topicData.id != UNKNOWN_TOPIC_ID) {
                narrow.add(
                    Narrow(
                        operator = "topic",
                        operand = topicData.topicName
                    )
                )
            }
            return Json.encodeToString(narrow)
        }
    }
}
