package com.homework.coursework.data.frameworks.network.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@ExperimentalSerializationApi
@Serializable
data class DtoResponse<out T>(
    val result: String,
    val msg: String?,
    @JsonNames(
        "streams",
        "subscriptions",
        "topics",
        "messages",
        "presence",
        "id",
        "user",
        "members",
        "subscribed",
        "uri"
    )
    val data: T? = null
)
