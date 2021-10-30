package com.homework.coursework.presentation.adapter.data

import com.homework.coursework.domain.entity.MessageData

data class MessageItem(
    val id: Int,
    val date: String?,
    val messageData: MessageData?
)
