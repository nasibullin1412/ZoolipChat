package com.homework.coursework.presentation.frameworks.database.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.homework.coursework.domain.entity.UserData

@Entity(
    tableName = "stream_table",
    foreignKeys = [ForeignKey(
        entity = TopicEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("topic_id"),
        onDelete = CASCADE
    )],
    indices = [Index()]
)
class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "message_id")
    val messageId: Int,
    @ColumnInfo(name = "user_data")
    val userData: UserData,
    @ColumnInfo(name = "message_content")
    val messageContent: String,
    @ColumnInfo(name = "message_content")
    val date: Long,
    @ColumnInfo(name = "is_current_user")
    val isCurrentUserMessage: Boolean,
    @ColumnInfo(name = "topic_id")
    val topicId: Int
)
