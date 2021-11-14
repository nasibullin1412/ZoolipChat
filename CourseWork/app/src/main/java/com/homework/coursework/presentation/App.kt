package com.homework.coursework.presentation

import android.app.Application
import android.content.Context
import com.homework.coursework.data.frameworks.network.ApiService
import kotlinx.serialization.ExperimentalSerializationApi

class App : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    @ExperimentalSerializationApi
    val apiService: ApiService by lazy { ApiService.create() }

    companion object {
        lateinit var instance: App
            private set
        lateinit var appContext: Context
            private set
    }
}
