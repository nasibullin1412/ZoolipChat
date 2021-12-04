package com.homework.coursework.data.frameworks.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "auth", indices = [Index(value = ["api_key"], unique = true)])
data class AuthEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "api_key")
    val apiKey: String,

    @ColumnInfo(name = "email")
    val email: String
)
