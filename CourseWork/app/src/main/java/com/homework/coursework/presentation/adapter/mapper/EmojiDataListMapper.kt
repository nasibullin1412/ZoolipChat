package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.EmojiData
import com.homework.coursework.presentation.adapter.data.EmojiItem

class EmojiDataListMapper : (List<EmojiItem?>) -> (List<EmojiData>) {
    private val emojiDataMapper: EmojiDataMapper = EmojiDataMapper()

    override fun invoke(emojiList: List<EmojiItem?>): List<EmojiData> {
        return emojiList.map { emojiDataMapper(it) }
    }
}
