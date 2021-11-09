package com.homework.coursework.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val result: String,
    val msg: String?,
    @SerialName("user_id")
    val id: Int,
    val email: String,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("avatar_url")
    val avatarUrl: String
)
