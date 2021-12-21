package com.homework.coursework.data.frameworks.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val result: String? = null,
    val msg: String? = null,
    @SerialName("user_id")
    val id: Int,
    val email: String,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    @SerialName("is_admin")
    val isAdmin: Boolean
)
