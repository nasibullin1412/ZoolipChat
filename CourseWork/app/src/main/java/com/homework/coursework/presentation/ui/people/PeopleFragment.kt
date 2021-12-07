package com.homework.coursework.presentation.ui.people

import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import com.homework.coursework.presentation.App
import com.homework.coursework.presentation.SearchListener
import com.homework.coursework.presentation.ui.people.elm.Effect
import com.homework.coursework.presentation.ui.people.elm.Event
import com.homework.coursework.presentation.ui.people.elm.State
import com.homework.coursework.presentation.utils.showToast
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class PeopleFragment : PeopleBaseFragment() {

    @Inject
    internal lateinit var peopleStore: Store<Event, Effect, State>

    @Inject
    internal lateinit var searchListener: SearchListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.peopleComponent().inject(this)
        searchListener.subscribeToSearchSubject({ setQuery(it) }, { showToast(it) })
    }

    private fun setQuery(query: String) {
        store.accept(Event.Ui.SearchUsers(query))
    }

    override fun initErrorRepeat() {
        binding.errorContent.tvRepeat.setOnClickListener { store.accept(Event.Ui.LoadUsers) }
    }

    override fun initSearch() {
        binding.etSearch.doAfterTextChanged { searchListener.searchTopics(it.toString()) }
    }

    override val initEvent: Event
        get() = Event.Ui.LoadUsers


    override fun createStore(): Store<Event, Effect, State> = peopleStore
}