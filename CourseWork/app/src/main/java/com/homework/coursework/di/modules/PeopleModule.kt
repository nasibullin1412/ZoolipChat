package com.homework.coursework.di.modules

import com.homework.coursework.di.PeopleFragmentScope
import com.homework.coursework.di.SearchPeopleSubject
import com.homework.coursework.domain.usecase.GetAllUsersUseCase
import com.homework.coursework.domain.usecase.SearchUsersUseCase
import com.homework.coursework.presentation.SearchListener
import com.homework.coursework.presentation.adapter.mapper.UserListMapper
import com.homework.coursework.presentation.people.elm.*
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import vivid.money.elmslie.core.store.Store

@Module
class PeopleModule {

    @PeopleFragmentScope
    @Provides
    fun providePeopleActor(
        getAllUsers: GetAllUsersUseCase,
        searchUsers: SearchUsersUseCase,
        userItemListMapper: UserListMapper
    ): PeopleActor {
        return PeopleActor(
            getAllUsers = getAllUsers,
            searchUsers = searchUsers,
            userItemListMapper = userItemListMapper
        )
    }

    @PeopleFragmentScope
    @Provides
    fun providePeopleStore(
        peopleActor: PeopleActor,
        peopleReducer: PeopleReducer
    ): Store<Event, Effect, State> {
        return PeopleStoreFactory(peopleActor, peopleReducer).provide()
    }

    @PeopleFragmentScope
    @SearchPeopleSubject
    @Provides
    fun provideSearchSubject(): PublishSubject<String> {
        return PublishSubject.create()
    }

    @PeopleFragmentScope
    @Provides
    fun provideSearchLogic(
        @SearchPeopleSubject
        searchSubject: PublishSubject<String>,
        compositeDisposable: CompositeDisposable
    ): SearchListener {
        return SearchListener(searchSubject, compositeDisposable)
    }
}
