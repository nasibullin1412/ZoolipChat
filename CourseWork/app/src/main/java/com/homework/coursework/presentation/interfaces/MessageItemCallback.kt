package com.homework.coursework.presentation.interfaces

import com.homework.coursework.presentation.adapter.data.EmojiItem

interface MessageItemCallback {
    fun getBottomSheet(messageId: Int)
    fun onEmojiClick(emojiItem: EmojiItem, messageId: Int)
}
