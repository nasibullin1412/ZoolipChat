package com.homework.coursework.presentation.ui.profile

import com.homework.coursework.R
import com.homework.coursework.domain.entity.StatusEnum

internal fun getStatusColor(status: StatusEnum?) = when (status) {
    StatusEnum.ACTIVE -> R.color.state_active_color
    StatusEnum.IDLE -> R.color.state_idle_color
    StatusEnum.OFFLINE -> R.color.state_offline_color
    StatusEnum.UNKNOWN -> R.color.state_offline_color
    null -> throw IllegalArgumentException("status null")
}

internal fun getStatusString(status: StatusEnum?) = when (status) {
    StatusEnum.ACTIVE -> "active"
    StatusEnum.IDLE -> "idle"
    StatusEnum.OFFLINE -> "offline"
    StatusEnum.UNKNOWN -> "unknown"
    null -> throw IllegalArgumentException("status null")
}
