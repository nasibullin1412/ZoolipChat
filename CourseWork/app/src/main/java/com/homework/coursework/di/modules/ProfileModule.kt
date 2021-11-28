package com.homework.coursework.di.modules

import com.homework.coursework.di.CurrUserStore
import com.homework.coursework.di.ProfileFragmentScope
import com.homework.coursework.di.UserStore
import com.homework.coursework.domain.usecase.GetMeUseCase
import com.homework.coursework.domain.usecase.GetUserProfileUseCase
import com.homework.coursework.presentation.adapter.mapper.UserItemMapper
import com.homework.coursework.presentation.profile.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.core.store.Store

@Module
class ProfileModule {

    @ProfileFragmentScope
    @Provides
    @UserStore
    fun provideProfileFragment(
        profileActor: ProfileActor,
        profileReducer: Reducer
    ): Store<Event, Effect, State> {
        return ProfileStoreFactory(profileActor, profileReducer).provide()
    }

    @ProfileFragmentScope
    @Provides
    fun provideProfileActor(
        getMe: GetMeUseCase,
        getUser: GetUserProfileUseCase,
        userItemMapper: UserItemMapper
    ): ProfileActor {
        return ProfileActor(getMe, getUser, userItemMapper)
    }

    @ProfileFragmentScope
    @Provides
    @CurrUserStore
    fun provideCurrProfileFragment(
        profileActor: ProfileActor,
        profileReducer: Reducer
    ): Store<Event, Effect, State> {
        return ProfileStoreFactory(profileActor, profileReducer).provide()
    }
}