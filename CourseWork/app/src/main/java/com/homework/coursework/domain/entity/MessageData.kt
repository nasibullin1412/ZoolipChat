package com.homework.coursework.domain.entity

data class MessageData(
    val messageId: Int,
    val userData: UserData,
    val messageContent: String,
    val emojis: List<EmojiData>,
    val date: Long,
    val isCurrentUserMessage: Boolean
)
