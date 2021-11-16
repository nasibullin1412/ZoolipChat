package com.homework.coursework.data.frameworks.database.entities

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.TEXT
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "stream_table", indices = [Index(value = ["stream_back_id"], unique = true)])
data class StreamEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "stream_back_id")
    val streamBackId: Long,
    @ColumnInfo(name = "stream_name", typeAffinity = TEXT)
    val streamName: String,
    @ColumnInfo(name = "description", typeAffinity = TEXT)
    val description: String,
    @ColumnInfo(name = "dateCreated")
    val dateCreated: Int,
    @ColumnInfo(name = "is_subscribed")
    val isSubscribed: Boolean
)
