package com.homework.coursework.presentation

import android.app.Application
import android.content.Context
import com.homework.coursework.di.DaggerApplicationComponent
import com.homework.coursework.di.GlobalDI
import com.homework.coursework.di.ApplicationComponent
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory().create(this.applicationContext)
        GlobalDI.init()
    }

    companion object {
        lateinit var appComponent: ApplicationComponent
            private set
    }
}
