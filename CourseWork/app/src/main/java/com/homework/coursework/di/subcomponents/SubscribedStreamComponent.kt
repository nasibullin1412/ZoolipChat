package com.homework.coursework.di.subcomponents

import com.homework.coursework.di.StreamFragmentScope
import com.homework.coursework.di.modules.RecycleViewPoolModule
import com.homework.coursework.di.modules.StreamModule
import com.homework.coursework.presentation.ui.stream.main.StreamSubscribedFragment
import dagger.Subcomponent

@StreamFragmentScope
@Subcomponent(modules = [StreamModule::class, RecycleViewPoolModule::class])
interface SubscribedStreamComponent {
    fun inject(streamFragment: StreamSubscribedFragment)
}
