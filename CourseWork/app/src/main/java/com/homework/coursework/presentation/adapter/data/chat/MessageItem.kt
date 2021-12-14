package com.homework.coursework.presentation.adapter.data.chat

import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.presentation.adapter.data.EmojiItem

data class MessageItem(
    val idItem: Int,
    val messageId: Int,
    val userData: UserData,
    val messageContent: String,
    val emojis: ArrayList<EmojiItem>,
    val date: Long,
    val isCurrentUserMessage: Boolean,
    val topicName: String
) : ChatItem(idItem)
