package com.homework.coursework.presentation.profile.main

import com.homework.coursework.di.GlobalDI
import com.homework.coursework.presentation.profile.BaseProfileFragment
import com.homework.coursework.presentation.profile.elm.Effect
import com.homework.coursework.presentation.profile.elm.Event
import com.homework.coursework.presentation.profile.elm.State
import kotlinx.serialization.ExperimentalSerializationApi
import vivid.money.elmslie.core.store.Store

@ExperimentalSerializationApi
class CurrUserProfileFragment : BaseProfileFragment() {

    override val initEvent: Event
        get() = Event.Ui.LoadMe

    override fun createStore(): Store<Event, Effect, State> =
        GlobalDI.instance.profileMeElmStoreFactory.provide()

    override fun initErrorRepeat() {
        binding.errorContent.tvRepeat.setOnClickListener {
            store.accept(Event.Ui.LoadMe)
        }
    }
}
