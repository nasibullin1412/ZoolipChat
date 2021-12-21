package com.homework.coursework.presentation.ui.profile

import com.homework.coursework.R
import com.homework.coursework.domain.entity.StatusEnum

internal fun getStatusColor(statusValue: Int) = when (StatusEnum.fromIntToStatus(statusValue)) {
    StatusEnum.ACTIVE -> R.color.state_active_color
    StatusEnum.IDLE -> R.color.state_idle_color
    StatusEnum.OFFLINE -> R.color.state_offline_color
    StatusEnum.UNKNOWN -> R.color.state_offline_color
}

internal fun getStatusString(statusValue: Int): String =
    when (StatusEnum.fromIntToStatus(statusValue)) {
        StatusEnum.ACTIVE -> "active"
        StatusEnum.IDLE -> "idle"
        StatusEnum.OFFLINE -> "offline"
        StatusEnum.UNKNOWN -> "unknown"
    }
