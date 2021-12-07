package com.homework.coursework.data.frameworks.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "current_profile",
    indices = [androidx.room.Index(value = ["user_id"], unique = true)]
)
data class CurrentProfileEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    val email: String,
    @ColumnInfo(name = "full_name", typeAffinity = ColumnInfo.TEXT)
    val fullName: String,
    @ColumnInfo(name = "avatar_url", typeAffinity = ColumnInfo.TEXT)
    val avatarUrl: String,
    @ColumnInfo(name = "user_status")
    val userStatus: Int,
    @ColumnInfo(name = "user_timestamp")
    val userTimestamp: Long,
    @ColumnInfo(name = "is_admin")
    val isAdmin: Boolean
)
