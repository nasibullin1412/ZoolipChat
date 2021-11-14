package com.homework.coursework.presentation.adapter.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class ErrorHandle(
    val isError: Boolean = false,
    val error: Throwable = UnknownError()
): Parcelable