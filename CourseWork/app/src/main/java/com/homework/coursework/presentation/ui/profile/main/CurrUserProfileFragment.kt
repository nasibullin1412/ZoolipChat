package com.homework.coursework.presentation.ui.profile.main

import android.os.Bundle
import android.view.View
import com.homework.coursework.di.CurrUserStore
import com.homework.coursework.presentation.App
import com.homework.coursework.presentation.ui.profile.BaseProfileFragment
import com.homework.coursework.presentation.ui.profile.elm.Effect
import com.homework.coursework.presentation.ui.profile.elm.Event
import com.homework.coursework.presentation.ui.profile.elm.State
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class CurrUserProfileFragment : BaseProfileFragment() {

    @Inject
    @CurrUserStore
    internal lateinit var currUserProfileStore: Store<Event, Effect, State>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.currUserProfileComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLogoutButton()
    }

    private fun initLogoutButton() {
        binding.btnLogout.setOnClickListener {
            store.accept(Event.Ui.LogoutUser)
        }
    }

    override fun onResume() {
        super.onResume()
        bottomNavigationController?.visibleBottomNavigation()
    }

    override val initEvent: Event
        get() = Event.Ui.LoadMe

    override fun createStore(): Store<Event, Effect, State> = currUserProfileStore

    override fun initErrorRepeat() {
        binding.errorContent.tvRepeat.setOnClickListener {
            store.accept(Event.Ui.LoadMe)
        }
    }
}
