package com.homework.coursework.di.modules

import com.homework.coursework.di.EditMessageFragmentScope
import com.homework.coursework.domain.usecase.message.EditMessageUseCase
import com.homework.coursework.presentation.adapter.mapper.MessageDataMapper
import com.homework.coursework.presentation.ui.editmessage.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.core.store.Store

@Module
class EditMessageModule {
    @EditMessageFragmentScope
    @Provides
    fun provideEditMessageActor(
        editMessageUseCase: EditMessageUseCase,
        messageDataMapper: MessageDataMapper
    ): EditMessageActor {
        return EditMessageActor(editMessageUseCase, messageDataMapper)
    }

    @EditMessageFragmentScope
    @Provides
    fun provideEditMessageStore(
        editMessageActor: EditMessageActor,
        editMessageReducer: EditMessageReducer
    ): Store<Event, Effect, State> {
        return EditMessageStoreFactory(editMessageActor, editMessageReducer).provide()
    }
}
