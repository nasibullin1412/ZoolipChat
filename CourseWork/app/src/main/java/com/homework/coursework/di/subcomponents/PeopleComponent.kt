package com.homework.coursework.di.subcomponents

import com.homework.coursework.di.PeopleFragmentScope
import com.homework.coursework.di.modules.PeopleModule
import com.homework.coursework.presentation.ui.people.PeopleFragment
import dagger.Subcomponent

@PeopleFragmentScope
@Subcomponent(modules = [PeopleModule::class])
interface PeopleComponent {
    fun inject(peopleFragment: PeopleFragment)
}
