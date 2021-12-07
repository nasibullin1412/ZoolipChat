package com.homework.coursework.di.modules

import com.homework.coursework.di.CurrUserStore
import com.homework.coursework.di.ProfileFragmentScope
import com.homework.coursework.di.UserStore
import com.homework.coursework.domain.usecase.GetMeUseCase
import com.homework.coursework.domain.usecase.GetUserProfileUseCase
import com.homework.coursework.domain.usecase.LogoutUserUseCase
import com.homework.coursework.presentation.adapter.mapper.UserItemMapper
import com.homework.coursework.presentation.ui.profile.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.core.store.Store

@Module
class ProfileModule {

    @ProfileFragmentScope
    @Provides
    @UserStore
    fun provideProfileStore(
        profileActor: ProfileActor,
        profileProfileReducer: ProfileReducer
    ): Store<Event, Effect, State> {
        return ProfileStoreFactory(profileActor, profileProfileReducer).provide()
    }

    @ProfileFragmentScope
    @Provides
    fun provideProfileActor(
        getMe: GetMeUseCase,
        getUser: GetUserProfileUseCase,
        logoutUser: LogoutUserUseCase,
        userItemMapper: UserItemMapper
    ): ProfileActor {
        return ProfileActor(getMe, getUser, logoutUser, userItemMapper)
    }

    @ProfileFragmentScope
    @Provides
    @CurrUserStore
    fun provideCurrProfileStore(
        profileActor: ProfileActor,
        profileProfileReducer: ProfileReducer
    ): Store<Event, Effect, State> {
        return ProfileStoreFactory(profileActor, profileProfileReducer).provide()
    }
}
