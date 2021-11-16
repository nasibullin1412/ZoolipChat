package com.homework.coursework.data.frameworks.database.entities

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.TEXT
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "message_table",
    indices = [Index(value = ["message_id"], unique = true)]
)
class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "message_id")
    val messageId: Int,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "message_content", typeAffinity = TEXT)
    val messageContent: String,
    @ColumnInfo(name = "date")
    val date: Long,
    @ColumnInfo(name = "is_current_user")
    val isCurrentUserMessage: Boolean,
    @ColumnInfo(name = "topic_name", typeAffinity = TEXT)
    val topicName: String,
    @ColumnInfo(name = "stream_id")
    val streamId: Int
)
