package com.homework.coursework.presentation.frameworks.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(
    tableName = "topic_table",
    foreignKeys = [ForeignKey(
        entity = StreamEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("stream_id"),
        onDelete = CASCADE
    )],
    indices = [Index(value = ["stream_id"])]
)
data class TopicEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "back_id")
    val backId: Long,
    @ColumnInfo(name = "topic_name")
    val topicName: String,
    @ColumnInfo(name = "stream_id")
    val streamId: Long,
    @ColumnInfo(name = "last_message")
    val numberOfMessage: Int
)
