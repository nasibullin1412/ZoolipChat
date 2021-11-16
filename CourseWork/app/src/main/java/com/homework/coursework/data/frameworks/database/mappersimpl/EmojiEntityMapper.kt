package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.EmojiEntity
import com.homework.coursework.domain.entity.EmojiData

class EmojiEntityMapper : (List<EmojiEntity>) -> (List<EmojiData>) {
    override fun invoke(emojis: List<EmojiEntity>): List<EmojiData> {
        val emojiMap = emojis.groupBy { it.emojiCode }
        val emojisData: ArrayList<EmojiData> = arrayListOf()
        for (key in emojiMap.keys) {
            emojisData.add(
                EmojiData(
                    emojiCode = key,
                    emojiNumber = emojiMap[key]?.size ?: 0,
                    emojiReactedId = emojiMap[key]?.map { it.userId } ?: listOf(),
                    emojiName = emojiMap[key]?.first()?.emojiName ?: ""
                )
            )
        }
        return emojisData
    }
}
