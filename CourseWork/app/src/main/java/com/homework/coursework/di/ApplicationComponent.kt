package com.homework.coursework.di

import android.content.Context
import com.homework.coursework.di.modules.DatabaseModule
import com.homework.coursework.di.modules.NetworkModule
import com.homework.coursework.di.modules.RepositoryModule
import com.homework.coursework.di.modules.UseCaseModule
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Named
import javax.inject.Singleton

@ExperimentalSerializationApi
@Singleton
@Component(
    modules = [NetworkModule::class, DatabaseModule::class, RepositoryModule::class,
        UseCaseModule::class]
)
interface ApplicationComponent {

    fun inject(globalDI: GlobalDI)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            context: Context
        ): ApplicationComponent
    }

    fun context(): Context

}
