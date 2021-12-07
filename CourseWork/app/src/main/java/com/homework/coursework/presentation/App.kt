package com.homework.coursework.presentation

import android.app.Application
import com.homework.coursework.di.ApplicationComponent
import com.homework.coursework.di.DaggerApplicationComponent
import kotlinx.serialization.ExperimentalSerializationApi

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory().create(this.applicationContext)
    }

    companion object {
        lateinit var appComponent: ApplicationComponent
            private set
    }
}
