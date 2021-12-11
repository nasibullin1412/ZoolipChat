package com.homework.coursework.di.subcomponents

import com.homework.coursework.di.CreateNewStreamFragmentScope
import com.homework.coursework.di.modules.CreateNewStreamModule
import com.homework.coursework.presentation.ui.createstream.main.CreateStreamFragment
import dagger.Subcomponent

@CreateNewStreamFragmentScope
@Subcomponent(modules = [CreateNewStreamModule::class])
interface CreateNewStreamComponent {
    fun inject(createStreamFragment: CreateStreamFragment)
}