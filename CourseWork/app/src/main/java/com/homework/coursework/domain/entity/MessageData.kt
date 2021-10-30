package com.homework.coursework.domain.entity

data class MessageData(
    val messageId: Int,
    val userData: UserData,
    val messageContent: String,
    val emojis: ArrayList<EmojiData>,
    val date: String
)
