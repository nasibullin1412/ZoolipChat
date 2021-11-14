package com.homework.coursework.data.frameworks.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_table",
    indices = [androidx.room.Index(value = ["user_id"], unique = true)]
)
data class UserEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    val email: String,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
    @ColumnInfo(name = "user_status")
    val userStatus: Int,
    @ColumnInfo(name = "user_timestamp")
    val userTimestamp: Long
)
