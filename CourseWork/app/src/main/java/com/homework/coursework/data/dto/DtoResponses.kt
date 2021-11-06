package com.homework.coursework.data.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@ExperimentalSerializationApi
@Serializable
data class DtoResponse<out T>(
    val result: String,
    val msg: String?,
    @JsonNames("streams", "subscriptions")
    val data: List<T>?
)
