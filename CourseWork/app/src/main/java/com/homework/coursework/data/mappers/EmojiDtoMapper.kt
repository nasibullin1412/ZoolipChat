package com.homework.coursework.data.mappers

import com.homework.coursework.data.dto.EmojiDto
import com.homework.coursework.domain.entity.EmojiData

class EmojiDtoMapper : (List<EmojiDto>) -> (List<EmojiData>) {
    override fun invoke(emojisDto: List<EmojiDto>): List<EmojiData> {
        val emojiMap = emojisDto.groupBy { it.emojiCode }
        val emojisData: ArrayList<EmojiData> = arrayListOf()
        for (key in emojiMap.keys) {
            val keyInt = key.toInt(16)
            emojisData.add(
                EmojiData(
                    emojiCode = String(Character.toChars(keyInt)),
                    emojiNumber = emojiMap.size,
                    emojiReactedId = emojiMap[key]?.map { it.userId } ?: listOf()
                )
            )
        }
        return emojisData
    }
}
