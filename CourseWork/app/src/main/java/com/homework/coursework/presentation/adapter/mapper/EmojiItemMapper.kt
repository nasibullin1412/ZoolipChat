package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.EmojiData
import com.homework.coursework.presentation.adapter.data.EmojiItem

class EmojiItemMapper : (List<EmojiData>, Int) -> (List<EmojiItem>) {
    override fun invoke(emojisData: List<EmojiData>, currUserId: Int): ArrayList<EmojiItem> {
        return ArrayList(
            emojisData.map { emojiData ->
                with(emojiData) {
                    EmojiItem(
                        emojiCode = emojiCode,
                        emojiNumber = emojiNumber,
                        isCurrUserReacted = emojiReactedId.contains(currUserId)
                    )
                }
            }
        )
    }
}