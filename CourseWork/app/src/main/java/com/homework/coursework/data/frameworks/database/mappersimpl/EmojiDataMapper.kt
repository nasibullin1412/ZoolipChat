package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.EmojiEntity
import com.homework.coursework.domain.entity.EmojiData

class EmojiDataMapper : (List<EmojiData>, Int) -> (List<EmojiEntity>) {
    override fun invoke(emojis: List<EmojiData>, messageId: Int): List<EmojiEntity> {
        val emojiEntityList: ArrayList<EmojiEntity> = arrayListOf()
        for (emoji in emojis) {
            emoji.emojiReactedId.forEach {
                val emojiEntity = EmojiEntity(
                    id = 0,
                    emojiName = emoji.emojiName,
                    emojiCode = emoji.emojiCode,
                    userId = it,
                    messageId = messageId
                )
                emojiEntityList.add(emojiEntity)
            }
        }
        return emojiEntityList
    }
}
