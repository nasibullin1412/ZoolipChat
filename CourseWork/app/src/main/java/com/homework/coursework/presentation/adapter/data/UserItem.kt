package com.homework.coursework.presentation.adapter.data

import android.os.Parcelable
import com.homework.coursework.domain.entity.UserStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserItem(
    val id: Int,
    val userId: Int,
    val name: String,
    val avatarUrl: String,
    val userMail: String,
    var userStatus: Int,
    val lastStatusDate: Long?,
    val isAdmin: Boolean,
    val errorHandle: ErrorHandle = ErrorHandle()
): Parcelable
