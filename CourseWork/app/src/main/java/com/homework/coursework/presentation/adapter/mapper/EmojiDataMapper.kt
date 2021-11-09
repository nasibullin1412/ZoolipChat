package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.EmojiData
import com.homework.coursework.presentation.adapter.data.EmojiItem

class EmojiDataMapper : (EmojiItem?) -> (EmojiData) {
    override fun invoke(emojiItem: EmojiItem?): EmojiData {
        return with(emojiItem ?: throw IllegalArgumentException("Emoji required")) {
            EmojiData(
                emojiCode = emojiCode,
                emojiName = emojiName,
                emojiReactedId = listOf(),
                emojiNumber = emojiNumber
            )
        }
    }
}
