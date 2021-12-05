package com.homework.coursework.data.frameworks.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.homework.coursework.data.frameworks.database.crossref.MessageToUserCrossRef
import com.homework.coursework.data.frameworks.database.dao.*
import com.homework.coursework.data.frameworks.database.entities.*
import com.homework.coursework.presentation.App
import kotlinx.serialization.ExperimentalSerializationApi

@Database(
    entities = [
        UserEntity::class,
        StreamEntity::class,
        TopicEntity::class,
        MessageEntity::class,
        EmojiEntity::class,
        MessageToUserCrossRef::class,
        AuthEntity::class,
        CurrentProfileEntity::class
    ],
    version = 7
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun streamDao(): StreamDao
    abstract fun messageDao(): MessageDao
    abstract fun messageToUserCrossRefDao(): MessageToUserCrossRefDao
    abstract fun apiKeyDao(): AuthDao
    abstract fun currentProfileDao(): CurrentProfileDao

    companion object {
        private const val DATABASE_NAME = "Streams.db"

        @ExperimentalSerializationApi
        val instance: AppDatabase by lazy {
            Room.databaseBuilder(
                App.appComponent.context(),
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
