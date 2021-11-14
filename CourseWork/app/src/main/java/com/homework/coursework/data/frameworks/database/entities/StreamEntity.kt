package com.homework.coursework.data.frameworks.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stream_table")
data class StreamEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "stream_id")
    val streamBackId: Long,
    @ColumnInfo(name = "stream_name")
    val streamName: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "dateCreated")
    val dateCreated: Int
)
