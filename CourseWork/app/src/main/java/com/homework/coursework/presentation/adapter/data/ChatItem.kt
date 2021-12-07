package com.homework.coursework.presentation.adapter.data

data class ChatItem(
    var id: Int,
    val date: String?,
    val messageItem: MessageItem?,
    var errorHandle: ErrorHandle = ErrorHandle()
)
