package com.homework.coursework.data.frameworks.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.homework.coursework.data.frameworks.database.crossref.MessageToUserCrossRef
import com.homework.coursework.data.frameworks.database.dao.MessageDao
import com.homework.coursework.data.frameworks.database.dao.MessageToUserCrossRefDao
import com.homework.coursework.data.frameworks.database.dao.StreamDao
import com.homework.coursework.data.frameworks.database.dao.UserDao
import com.homework.coursework.data.frameworks.database.entities.*
import com.homework.coursework.presentation.App

@Database(
    entities = [
        UserEntity::class,
        StreamEntity::class,
        TopicEntity::class,
        MessageEntity::class,
        EmojiEntity::class,
        MessageToUserCrossRef::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun streamDao(): StreamDao
    abstract fun messageDao(): MessageDao
    abstract fun messageToUserCrossRefDao(): MessageToUserCrossRefDao

    companion object {
        private const val DATABASE_NAME = "Streams.db"
        val instance: AppDatabase by lazy {
            Room.databaseBuilder(
                App.appContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
