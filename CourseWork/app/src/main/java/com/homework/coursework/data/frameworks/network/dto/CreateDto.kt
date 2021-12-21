package com.homework.coursework.data.frameworks.network.dto

import com.homework.coursework.domain.entity.SubscribeData
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class CreateDto(
    val name: String,
    val description: String
) {
    companion object {
        fun createNarrowForMessage(subscribeData: SubscribeData) = Json.encodeToString(
            listOf(
                CreateDto(
                    name = subscribeData.name,
                    description = subscribeData.description
                )
            )
        )
    }
}
