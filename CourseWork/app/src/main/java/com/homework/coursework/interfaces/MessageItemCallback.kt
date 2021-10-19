package com.homework.coursework.interfaces

interface MessageItemCallback {
    fun getBottomSheet(messageId: Int): Boolean
    fun onEmojiClick(emojiCode: String, idx: Int): Boolean
}
