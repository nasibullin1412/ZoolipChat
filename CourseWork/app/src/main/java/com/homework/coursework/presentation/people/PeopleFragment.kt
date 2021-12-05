package com.homework.coursework.presentation.people

import android.os.Bundle
import com.homework.coursework.presentation.App
import com.homework.coursework.presentation.people.elm.Effect
import com.homework.coursework.presentation.people.elm.Event
import com.homework.coursework.presentation.people.elm.State
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class PeopleFragment : PeopleBaseFragment() {

    @Inject
    internal lateinit var peopleStore: Store<Event, Effect, State>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.peopleComponent().inject(this)
    }

    override fun initErrorRepeat() {
    }

    override fun initSearch() {
        binding
    }

    override val initEvent: Event
        get() = Event.Ui.LoadUsers

    override fun createStore(): Store<Event, Effect, State> = peopleStore
}