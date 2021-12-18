package com.homework.coursework.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UserStatus(
    val status: StatusEnum = StatusEnum.UNKNOWN,
    val timestamp: Long = 0,
): Parcelable
