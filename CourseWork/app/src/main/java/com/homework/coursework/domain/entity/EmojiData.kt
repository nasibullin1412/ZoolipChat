package com.homework.coursework.domain.entity

data class EmojiData(
    val emojiCode: String,
    var emojiNumber: Int,
    val emojiReactedId: List<Int>
)
