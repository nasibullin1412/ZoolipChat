package com.homework.coursework.data

data class MessageData(
    val messageId: Int,
    val userData: UserData,
    val messageContent: String,
    val emojis: ArrayList<EmojiData>,
    val date: String
)
