package com.homework.coursework.domain.entity

class UserStatus(
    val status: StatusEnum = StatusEnum.UNKNOWN,
    val timestamp: Long = 0,
)
