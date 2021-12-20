package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.EmojiEntity
import com.homework.coursework.domain.entity.EmojiData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class EmojiEntityMapper @Inject constructor() : (List<EmojiEntity>) -> (List<EmojiData>) {
    override fun invoke(emojis: List<EmojiEntity>): List<EmojiData> {
        val emojisData: ArrayList<EmojiData> = arrayListOf()
        for (emoji in emojis) {
            with(emoji) {
                val emojiReactedId = List(emojiNumber) { it + 1 }
                emojisData.add(
                    EmojiData(
                        emojiCode = emojiCode,
                        emojiNumber = emojiNumber,
                        emojiReactedId = emojiReactedId,
                        emojiName = emojiName
                    )
                )
            }
        }
        return emojisData
    }
}
