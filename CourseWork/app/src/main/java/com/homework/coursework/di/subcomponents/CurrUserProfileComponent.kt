package com.homework.coursework.di.subcomponents

import com.homework.coursework.di.ProfileFragmentScope
import com.homework.coursework.di.modules.ProfileModule
import com.homework.coursework.presentation.ui.profile.main.CurrUserProfileFragment
import dagger.Subcomponent
import kotlinx.serialization.ExperimentalSerializationApi

@ProfileFragmentScope
@Subcomponent(modules = [ProfileModule::class])
@ExperimentalSerializationApi
interface CurrUserProfileComponent {
    fun inject(userProfileFragment: CurrUserProfileFragment)
}
