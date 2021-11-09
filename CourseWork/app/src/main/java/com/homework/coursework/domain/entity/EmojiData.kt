package com.homework.coursework.domain.entity

data class EmojiData(
    val emojiCode: String,
    val emojiName: String,
    var emojiNumber: Int,
    val emojiReactedId: List<Int>
)
