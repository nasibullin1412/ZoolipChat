package com.homework.coursework.di.modules

import com.homework.coursework.di.CreateNewStreamFragmentScope
import com.homework.coursework.domain.usecase.streams.SubscribeToStreamUseCase
import com.homework.coursework.presentation.ui.createstream.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.core.store.Store

@Module
class CreateNewStreamModule {

    @CreateNewStreamFragmentScope
    @Provides
    fun provideCreateStreamActor(
        subscribeToStreamUseCase: SubscribeToStreamUseCase
    ): CreateStreamActor {
        return CreateStreamActor(
            subscribeToStreamUseCase
        )
    }

    @CreateNewStreamFragmentScope
    @Provides
    fun provideSubscribedStreamStore(
        createStreamActor: CreateStreamActor,
        createStreamReducer: CreateStreamReducer
    ): Store<Event, Effect, State> {
        return CreateStreamStoreFactory(createStreamActor, createStreamReducer).provide()
    }
}
