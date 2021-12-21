package com.homework.coursework.data.frameworks.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopicDto(
    val name: String,
    @SerialName("max_id")
    val maxId: Int
)
