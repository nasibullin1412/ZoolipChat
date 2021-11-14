package com.homework.coursework.domain.entity

data class ErrorHandle(
    val isError: Boolean = false,
    val error: Throwable = UnknownError()
)
