package com.homework.coursework.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class StatusDto(
    val aggregated: Client,
    val website: Client
) {
    companion object {
        fun getEmptyStatus(): StatusDto {
            return StatusDto(
                aggregated = Client(status = "offline", timestamp = 0),
                website = Client(status = "offline", timestamp = 0)
            )
        }
    }
}

@Serializable
data class Client(
    val status: String,
    val timestamp: Long
) {
    fun isActive() = status == STATUS_ACTIVE
    fun isIdle() = status == STATUS_IDLE
    fun isOffline() = status == STATUS_OFFLINE

    companion object {
        private const val STATUS_ACTIVE = "active"
        private const val STATUS_OFFLINE = "offline"
        private const val STATUS_IDLE = "idle"
    }
}
