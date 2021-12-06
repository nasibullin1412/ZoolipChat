package com.homework.coursework.di.subcomponents

import com.homework.coursework.di.StreamMainFragmentScope
import com.homework.coursework.di.modules.StreamMainModule
import com.homework.coursework.presentation.stream.StreamFragment
import dagger.Subcomponent

@StreamMainFragmentScope
@Subcomponent(modules = [StreamMainModule::class])
interface StreamMainComponent {
    fun inject(streamFragment: StreamFragment)
}
