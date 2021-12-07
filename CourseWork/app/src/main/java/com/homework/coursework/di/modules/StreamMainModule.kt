package com.homework.coursework.di.modules

import com.homework.coursework.di.SearchStreamSubject
import com.homework.coursework.di.StreamMainFragmentScope
import com.homework.coursework.presentation.utils.SearchListener
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

@Module
class StreamMainModule {

    @StreamMainFragmentScope
    @SearchStreamSubject
    @Provides
    fun provideSearchSubject(): PublishSubject<String> {
        return PublishSubject.create()
    }

    @StreamMainFragmentScope
    @Provides
    fun provideSearchLogic(
        @SearchStreamSubject
        searchSubject: PublishSubject<String>,
        compositeDisposable: CompositeDisposable
    ): SearchListener {
        return SearchListener(searchSubject, compositeDisposable)
    }
}
