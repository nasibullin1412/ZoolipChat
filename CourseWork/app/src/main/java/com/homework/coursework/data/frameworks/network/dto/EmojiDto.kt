package com.homework.coursework.data.frameworks.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmojiDto(
    @SerialName("emoji_name")
    val emojiName: String,
    @SerialName("emoji_code")
    val emojiCode: String,
    @SerialName("user_id")
    val userId: Int
)
