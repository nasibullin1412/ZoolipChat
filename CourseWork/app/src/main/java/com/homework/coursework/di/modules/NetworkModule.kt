package com.homework.coursework.di.modules

import com.homework.coursework.data.frameworks.database.dao.AuthDao
import com.homework.coursework.data.frameworks.network.ApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideApiService(authDao: AuthDao): ApiService {
        return ApiService.create(authDao)
    }
}
