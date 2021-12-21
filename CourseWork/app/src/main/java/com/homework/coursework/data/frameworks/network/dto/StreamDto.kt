package com.homework.coursework.data.frameworks.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamDto(
    val name: String,
    @SerialName("stream_id")
    val streamId: Int,
    val description: String,
    @SerialName("first_message_id")
    val firstMessageId: Int,
    @SerialName("date_created")
    val dateCreated: Int
)
