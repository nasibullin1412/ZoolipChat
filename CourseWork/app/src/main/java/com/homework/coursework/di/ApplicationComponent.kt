package com.homework.coursework.di

import android.content.Context
import com.homework.coursework.di.modules.*
import com.homework.coursework.di.subcomponents.AllStreamComponent
import com.homework.coursework.di.subcomponents.CurrUserProfileComponent
import com.homework.coursework.di.subcomponents.SubscribedStreamComponent
import com.homework.coursework.di.subcomponents.UserProfileComponent
import dagger.BindsInstance
import dagger.Component
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@ExperimentalSerializationApi
@Singleton
@Component(
    modules = [NetworkModule::class, DatabaseModule::class, RepositoryModule::class,
        UseCaseModule::class, CompositeModule::class]
)
interface ApplicationComponent {

    fun inject(globalDI: GlobalDI)

    fun userProfileComponent(): UserProfileComponent

    fun currUserProfileComponent(): CurrUserProfileComponent

    fun allStreamsComponent(): AllStreamComponent

    fun subscribedStreamsComponent(): SubscribedStreamComponent

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            context: Context
        ): ApplicationComponent
    }

    fun context(): Context
}
