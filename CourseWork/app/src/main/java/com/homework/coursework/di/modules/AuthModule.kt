package com.homework.coursework.di.modules

import com.homework.coursework.di.AuthScope
import com.homework.coursework.domain.usecase.AuthUserUseCase
import com.homework.coursework.domain.usecase.CheckAuthUseCase
import com.homework.coursework.presentation.authorization.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.core.store.Store

@Module
class AuthModule {

    @AuthScope
    @Provides
    fun provideAuthActor(
        authUserUseCase: AuthUserUseCase,
        isCheckAuthUseCase: CheckAuthUseCase
    ): AuthActor {
        return AuthActor(authUserUseCase, isCheckAuthUseCase)
    }

    @AuthScope
    @Provides
    fun provideCurrProfileStore(
        authActor: AuthActor,
        authReducer: AuthReducer
    ): Store<Event, Effect, State> {
        return AuthStoreFactory(authActor, authReducer).provide()
    }
}
