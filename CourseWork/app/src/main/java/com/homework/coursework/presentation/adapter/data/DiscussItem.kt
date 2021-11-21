package com.homework.coursework.presentation.adapter.data

data class DiscussItem(
    var id: Int,
    val date: String?,
    val messageItem: MessageItem?,
    var errorHandle: ErrorHandle = ErrorHandle()
)
