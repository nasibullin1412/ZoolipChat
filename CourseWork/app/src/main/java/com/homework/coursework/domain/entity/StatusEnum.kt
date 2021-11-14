package com.homework.coursework.domain.entity

enum class StatusEnum(val value: Int) {
    ACTIVE(0),
    IDLE(1),
    OFFLINE(2),
    UNKNOWN(3);

    companion object {
        fun fromIntToStatus(value: Int) = values().first { it.value == value }
    }
}
