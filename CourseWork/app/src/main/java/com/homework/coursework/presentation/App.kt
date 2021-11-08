package com.homework.coursework.presentation

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.homework.coursework.presentation.frameworks.network.ApiService
import kotlinx.serialization.ExperimentalSerializationApi

class App : Application() {

    init {
        instance = this
    }

    @ExperimentalSerializationApi
    val apiService: ApiService by lazy { ApiService.create() }

    companion object {
        lateinit var instance: App
            private set
    }
}
