package com.homework.coursework.presentation.adapter.data

import com.homework.coursework.domain.entity.UserStatus

data class UserItem(
    val id: Int,
    val name: String,
    val avatarUrl: String,
    val userMail: String,
    var userStatus: UserStatus?,
    val lastStatusDate: Long?,
    val errorHandle: ErrorHandle = ErrorHandle()
)
