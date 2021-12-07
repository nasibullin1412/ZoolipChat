package com.homework.coursework.di.subcomponents

import com.homework.coursework.di.AuthScope
import com.homework.coursework.di.modules.AuthModule
import com.homework.coursework.presentation.ui.authorization.main.AuthFragment
import dagger.Subcomponent
import kotlinx.serialization.ExperimentalSerializationApi

@AuthScope
@Subcomponent(modules = [AuthModule::class])
@ExperimentalSerializationApi
interface AuthComponent {
    fun inject(authFragment: AuthFragment)
}
