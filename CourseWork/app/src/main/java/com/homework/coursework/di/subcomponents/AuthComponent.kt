package com.homework.coursework.di.subcomponents

import com.homework.coursework.di.AuthScope
import com.homework.coursework.di.modules.AuthModule
import com.homework.coursework.presentation.ui.authorization.main.AuthFragment
import dagger.Subcomponent

@AuthScope
@Subcomponent(modules = [AuthModule::class])
interface AuthComponent {
    fun inject(authFragment: AuthFragment)
}
