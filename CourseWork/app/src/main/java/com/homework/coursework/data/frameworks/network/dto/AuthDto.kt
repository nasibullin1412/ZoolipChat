package com.homework.coursework.data.frameworks.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthDto(
    val result: String? = null,
    val msg: String? = null,
    @SerialName("api_key")
    val apiKey: String,
    @SerialName("email")
    val email: String
)
