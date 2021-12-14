package com.homework.coursework.di.modules

import com.homework.coursework.di.AuthScope
import com.homework.coursework.domain.usecase.auth.AuthUserUseCase
import com.homework.coursework.domain.usecase.auth.CheckAuthUseCase
import com.homework.coursework.domain.usecase.users.GetMeUseCase
import com.homework.coursework.presentation.ui.authorization.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.core.store.Store

@Module
class AuthModule {

    @AuthScope
    @Provides
    fun provideAuthActor(
        authUserUseCase: AuthUserUseCase,
        isCheckAuthUseCase: CheckAuthUseCase,
        getMeUseCase: GetMeUseCase
    ): AuthActor {
        return AuthActor(authUserUseCase, isCheckAuthUseCase, getMeUseCase)
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
