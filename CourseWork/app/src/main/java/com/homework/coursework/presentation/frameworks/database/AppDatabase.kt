package com.homework.coursework.presentation.frameworks.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.homework.coursework.presentation.App

abstract class AppDatabase : RoomDatabase() {
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
