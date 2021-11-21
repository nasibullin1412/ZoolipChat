package com.homework.coursework.presentation.profile.main

import android.os.Bundle
import com.homework.coursework.di.GlobalDI
import com.homework.coursework.presentation.profile.BaseProfileFragment
import com.homework.coursework.presentation.profile.elm.Effect
import com.homework.coursework.presentation.profile.elm.Event
import com.homework.coursework.presentation.profile.elm.State
import kotlinx.serialization.ExperimentalSerializationApi
import vivid.money.elmslie.core.store.Store

@ExperimentalSerializationApi
class UserProfileFragment : BaseProfileFragment() {

    private var userId = DEFAULT_VALUE

    override fun onCreate(savedInstanceState: Bundle?) {
        userId = requireArguments().getInt(USER_ID_KEY, DEFAULT_VALUE)
        super.onCreate(savedInstanceState)
    }

    override val initEvent: Event
        get() = Event.Ui.LoadUser(userId)

    override fun createStore(): Store<Event, Effect, State> =
        GlobalDI.instance.profileElmStoreFactory.provide()

    override fun initErrorRepeat() {
        binding.errorContent.tvRepeat.setOnClickListener {
            store.accept(Event.Ui.LoadUser(userId))
        }
    }

    companion object {
        const val USER_ID_KEY = "UserId"
        const val DEFAULT_VALUE = -1
        fun newInstance(userId: Int): UserProfileFragment {
            val args = Bundle()
            args.putInt(USER_ID_KEY, userId)
            val fragment = UserProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
