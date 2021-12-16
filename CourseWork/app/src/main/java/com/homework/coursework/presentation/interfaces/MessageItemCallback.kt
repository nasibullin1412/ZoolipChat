package com.homework.coursework.presentation.interfaces

import com.homework.coursework.presentation.adapter.data.EmojiItem
import com.homework.coursework.presentation.adapter.data.chat.MessageItem

interface MessageItemCallback {
    fun getBottomSheet(messageItem: MessageItem): Boolean
    fun onEmojiClick(emojiItem: EmojiItem, messageId: Int)
}
