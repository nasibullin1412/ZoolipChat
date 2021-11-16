package com.homework.coursework.data.frameworks.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(
    tableName = "emoji_table",
    foreignKeys = [ForeignKey(
        entity = MessageEntity::class,
        parentColumns = arrayOf("message_id"),
        childColumns = arrayOf("emoji_message_id"),
        onDelete = CASCADE
    )],
    indices = [Index(value = ["emoji_message_id"])]
)
data class EmojiEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "emoji_name")
    val emojiName: String,
    @ColumnInfo(name = "emoji_code")
    val emojiCode: String,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "emoji_message_id")
    val messageId: Int
)
