package com.homework.coursework.di.modules

import com.homework.coursework.data.frameworks.network.ApiService
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@Module
class NetworkModule {
    var value: Int = 0

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return ApiService.instance
    }
}
