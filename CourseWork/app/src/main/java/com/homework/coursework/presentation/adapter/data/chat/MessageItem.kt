package com.homework.coursework.presentation.adapter.data.chat

import android.os.Parcelable
import com.homework.coursework.presentation.adapter.data.EmojiItem
import com.homework.coursework.presentation.adapter.data.UserItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageItem(
    val messageId: Int,
    val userItem: UserItem,
    var messageContent: String,
    val emojis: ArrayList<EmojiItem>,
    val date: Long,
    val isCurrentUserMessage: Boolean,
    var topicName: String
) : ChatItem(), Parcelable
