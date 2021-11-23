package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.EmojiEntity
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.USER_ID
import com.homework.coursework.domain.entity.EmojiData

class EmojiDataMapper : (List<EmojiData>, Int) -> (List<EmojiEntity>) {
    override fun invoke(emojis: List<EmojiData>, messageId: Int): List<EmojiEntity> {
        val emojiEntityList: ArrayList<EmojiEntity> = arrayListOf()
        for (emoji in emojis) {
            with(emoji) {
                emojiEntityList.add(
                    EmojiEntity(
                        id = 0,
                        emojiName = emojiName,
                        emojiCode = emojiCode,
                        messageId = messageId,
                        emojiNumber = emojiNumber,
                        isMe = emoji.emojiReactedId.contains(USER_ID)
                    )
                )
            }
        }
        return emojiEntityList
    }
}
