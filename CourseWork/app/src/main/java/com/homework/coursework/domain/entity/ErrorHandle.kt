package com.homework.coursework.domain.entity

class ErrorHandle(
    val isError: Boolean = false,
    val error: Throwable = UnknownError()
)
