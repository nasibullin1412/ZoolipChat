package com.homework.coursework.data.frameworks.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.homework.coursework.presentation.App
import com.homework.coursework.data.frameworks.database.dao.UserDao
import com.homework.coursework.data.frameworks.database.entities.UserEntity

@Database(entities = [
    UserEntity::class
],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
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
