package com.homework.coursework.data.frameworks.database.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.homework.coursework.data.frameworks.database.crossref.MessageToUserCrossRef

data class MessageWithEmojiEntity(
    @Embedded val messageEntity: MessageEntity,
    @Relation(parentColumn = "message_id", entityColumn = "emoji_message_id")
    val emojiEntity: List<EmojiEntity>,
    @Relation(
        parentColumn = "message_id",
        entityColumn = "user_id",
        associateBy = Junction(MessageToUserCrossRef::class)
    )
    val userEntity: UserEntity
)
