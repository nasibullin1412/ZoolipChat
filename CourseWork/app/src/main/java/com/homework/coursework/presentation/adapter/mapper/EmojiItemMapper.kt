package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.EmojiData
import com.homework.coursework.presentation.adapter.data.EmojiItem
import dagger.Reusable
import javax.inject.Inject

@Reusable
class EmojiItemMapper @Inject constructor() : (List<EmojiData>, Int) -> (List<EmojiItem>) {
    override fun invoke(emojisData: List<EmojiData>, currUserId: Int): ArrayList<EmojiItem> {
        return ArrayList(
            emojisData.reversed().map { emojiData ->
                with(emojiData) {
                    EmojiItem(
                        emojiCode = emojiCode,
                        emojiNumber = emojiNumber,
                        isCurrUserReacted = emojiReactedId.contains(currUserId),
                        emojiName = emojiName
                    )
                }
            }
        )
    }
}
