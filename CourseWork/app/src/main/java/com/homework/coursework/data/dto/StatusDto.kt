package com.homework.coursework.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class StatusDto(
    val aggregated: Client,
    val website: Client
)

@Serializable
data class Client(
    val status: String,
    val timestamp: Long
){
    fun isActive() = status == STATUS_ACTIVE
    fun isIdle() = status == STATUS_IDLE
    fun isOffline() = status == STATUS_OFFLINE
    companion object{
        private const val STATUS_ACTIVE = "active"
        private const val STATUS_OFFLINE = "offline"
        private const val STATUS_IDLE = "idle"
    }
}
