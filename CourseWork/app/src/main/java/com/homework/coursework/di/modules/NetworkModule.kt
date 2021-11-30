package com.homework.coursework.di.modules

import com.homework.coursework.data.frameworks.database.dao.ApiKeyDao
import com.homework.coursework.data.frameworks.network.ApiService
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@Module
class NetworkModule {
    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideApiService(apiKeyDao: ApiKeyDao): ApiService {
        return ApiService.create(apiKeyDao)
    }
}
