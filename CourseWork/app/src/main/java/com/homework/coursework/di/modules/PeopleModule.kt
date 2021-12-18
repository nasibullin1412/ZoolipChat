package com.homework.coursework.di.modules

import com.homework.coursework.di.*
import com.homework.coursework.domain.usecase.users.GetAllUsersUseCase
import com.homework.coursework.domain.usecase.users.SearchUsersUseCase
import com.homework.coursework.presentation.utils.SearchListener
import com.homework.coursework.presentation.adapter.mapper.UserListMapper
import com.homework.coursework.presentation.ui.people.elm.*
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
    @PeopleSearch
    @Provides
    fun provideComposite(): CompositeDisposable{
        return CompositeDisposable()
    }

    @PeopleFragmentScope
    @Provides
    fun provideSearchLogic(
        @SearchPeopleSubject
        searchSubject: PublishSubject<String>,
        @PeopleSearch
        compositeDisposable: CompositeDisposable
    ): SearchListener {
        return SearchListener(searchSubject, compositeDisposable)
    }
}
