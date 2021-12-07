package com.homework.coursework.di.subcomponents

import com.homework.coursework.di.ProfileFragmentScope
import com.homework.coursework.di.modules.ProfileModule
import com.homework.coursework.presentation.ui.profile.main.UserProfileFragment
import dagger.Subcomponent

@ProfileFragmentScope
@Subcomponent(modules = [ProfileModule::class])
interface UserProfileComponent {
    fun inject(userProfileFragment: UserProfileFragment)
}
