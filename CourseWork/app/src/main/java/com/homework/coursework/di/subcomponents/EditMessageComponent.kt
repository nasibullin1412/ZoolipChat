package com.homework.coursework.di.subcomponents

import com.homework.coursework.di.EditMessageFragmentScope
import com.homework.coursework.di.modules.EditMessageModule
import com.homework.coursework.presentation.ui.editmessage.main.EditMessageFragment
import dagger.Subcomponent

@EditMessageFragmentScope
@Subcomponent(modules = [EditMessageModule::class])
interface EditMessageComponent {
    fun inject(editMessageFragment: EditMessageFragment)
}
