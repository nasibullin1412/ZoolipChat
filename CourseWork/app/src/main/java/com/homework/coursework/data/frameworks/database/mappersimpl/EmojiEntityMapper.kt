package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.EmojiEntity
import com.homework.coursework.domain.entity.EmojiData

class EmojiEntityMapper : (List<EmojiEntity>) -> (List<EmojiData>) {
    override fun invoke(emojis: List<EmojiEntity>): List<EmojiData> {
        val emojisData: ArrayList<EmojiData> = arrayListOf()
        for (emoji in emojis) {
            val emojiReactedId = List(emoji.emojiNumber) { it + 1 }
            emojisData.add(
                EmojiData(
                    emojiCode = emoji.emojiCode,
                    emojiNumber = emoji.emojiNumber,
                    emojiReactedId = emojiReactedId,
                    emojiName = emoji.emojiName
                )
            )
        }
        return emojisData
    }
}
