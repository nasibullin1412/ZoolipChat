package com.homework.coursework.presentation.adapter.data

import com.homework.coursework.domain.entity.UserData

data class MessageItem(
    val messageId: Int,
    val userData: UserData,
    val messageContent: String,
    val emojis: ArrayList<EmojiItem>,
    val date: Long,
    val isCurrentUserMessage: Boolean
)
