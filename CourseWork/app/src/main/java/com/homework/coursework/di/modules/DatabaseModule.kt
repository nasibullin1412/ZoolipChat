package com.homework.coursework.di.modules

import com.homework.coursework.data.frameworks.database.AppDatabase
import com.homework.coursework.data.frameworks.database.dao.MessageDao
import com.homework.coursework.data.frameworks.database.dao.MessageToUserCrossRefDao
import com.homework.coursework.data.frameworks.database.dao.StreamDao
import com.homework.coursework.data.frameworks.database.dao.UserDao
import dagger.Lazy

import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@Module
@ExperimentalSerializationApi
class DatabaseModule {

    @Singleton
    @Provides
    fun bindDatabase(): AppDatabase = AppDatabase.instance

    @Singleton
    @Provides
    fun bindMessageDao(database: Lazy<AppDatabase>): MessageDao = database.get().messageDao()

    @Singleton
    @Provides
    fun bindMessageToUserCrossRefDao(database: Lazy<AppDatabase>): MessageToUserCrossRefDao {
        return database.get().messageToUserCrossRefDao()
    }

    @Singleton
    @Provides
    fun bindStreamDao(database: Lazy<AppDatabase>): StreamDao = database.get().streamDao()

    @Singleton
    @Provides
    fun bindUserDao(database: Lazy<AppDatabase>): UserDao = database.get().userDao()

}
