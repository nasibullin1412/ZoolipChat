package com.homework.coursework.data.frameworks.network.mappersimpl

import com.homework.coursework.data.frameworks.network.dto.EmojiDto
import com.homework.coursework.domain.entity.EmojiData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class EmojiDtoMapper @Inject constructor() : (List<EmojiDto>) -> (List<EmojiData>) {
    override fun invoke(emojisDto: List<EmojiDto>): List<EmojiData> {
        val emojiMap = emojisDto.groupBy { it.emojiCode }
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
