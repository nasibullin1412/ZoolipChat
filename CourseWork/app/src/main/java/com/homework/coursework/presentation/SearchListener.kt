package com.homework.coursework.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchListener(
    private val searchSubject: PublishSubject<String>,
    val compositeDisposable: CompositeDisposable
) {

    fun subscribeToSearchSubject(
        setQuery: (String) -> Unit,
        showToast: (String) -> Unit
    ) {
        searchSubject
            .subscribeOn(Schedulers.io())
            .distinctUntilChanged()
            .debounce(TIME_FOR_WAIT, TimeUnit.MILLISECONDS, Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { query -> setQuery(query) },
                onError = { error -> showToast(error.message.toString()) }
            )
            .addTo(compositeDisposable)
    }

    fun searchTopics(query: String) {
        searchSubject.onNext(query)
    }

    companion object {
        const val TIME_FOR_WAIT = 500.toLong()
    }
}
