package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.EmojiData
import com.homework.coursework.presentation.adapter.data.EmojiItem
import dagger.Reusable
import javax.inject.Inject

@Reusable
class EmojiDataMapper @Inject constructor() : (EmojiItem?) -> (EmojiData) {
    override fun invoke(emojiItem: EmojiItem?): EmojiData {
        return with(emojiItem ?: throw IllegalArgumentException("Emoji required")) {
            EmojiData(
                emojiCode = emojiCode,
                emojiName = emojiName,
                emojiReactedId = List(5) { it + 1 },
                emojiNumber = emojiNumber
            )
        }
    }
}
