package com.homework.coursework.di.subcomponents

import com.homework.coursework.di.ChatFragmentScope
import com.homework.coursework.di.modules.ChatModule
import com.homework.coursework.presentation.ui.chat.main.StreamChatFragment
import dagger.Subcomponent

@ChatFragmentScope
@Subcomponent(modules = [ChatModule::class])
interface StreamChatComponent {
    fun inject(streamChatFragment: StreamChatFragment)
}
