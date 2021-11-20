package com.homework.coursework.presentation

import android.app.Application
import android.content.Context
import com.homework.coursework.di.GlobalDI
import kotlinx.serialization.ExperimentalSerializationApi

class App : Application() {

    @ExperimentalSerializationApi
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        GlobalDI.init()
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}
