package com.homework.coursework.presentation.interfaces

import com.homework.coursework.presentation.adapter.data.EmojiItem

interface MessageItemCallback {
    fun getBottomSheet(messageId: Int): Boolean
    fun onEmojiClick(emojiItem: EmojiItem, idx: Int): Boolean
}
