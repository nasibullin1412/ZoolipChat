package com.homework.coursework.di.subcomponents

import com.homework.coursework.di.ProfileFragmentScope
import com.homework.coursework.di.modules.ProfileModule
import com.homework.coursework.presentation.profile.main.UserProfileFragment
import dagger.Subcomponent
import kotlinx.serialization.ExperimentalSerializationApi

@ProfileFragmentScope
@Subcomponent(modules = [ProfileModule::class])
@ExperimentalSerializationApi
interface UserProfileComponent {
    fun inject(userProfileFragment: UserProfileFragment)
}
