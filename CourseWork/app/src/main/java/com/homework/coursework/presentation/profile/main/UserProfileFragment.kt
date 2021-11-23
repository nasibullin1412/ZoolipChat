package com.homework.coursework.presentation.profile.main

import android.os.Bundle
import android.view.View
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.SOME_ANOTHER_USER_ID
import com.homework.coursework.di.GlobalDI
import com.homework.coursework.presentation.profile.BaseProfileFragment
import com.homework.coursework.presentation.profile.elm.Effect
import com.homework.coursework.presentation.profile.elm.Event
import com.homework.coursework.presentation.profile.elm.State
import kotlinx.serialization.ExperimentalSerializationApi
import vivid.money.elmslie.core.store.Store

@ExperimentalSerializationApi
class UserProfileFragment : BaseProfileFragment() {

    private var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        userId = requireArguments().getInt(USER_ID_KEY, SOME_ANOTHER_USER_ID)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogout.visibility = View.GONE
    }

    override val initEvent: Event
        get() = Event.Ui.LoadUser(userId)

    override fun createStore(): Store<Event, Effect, State> =
        GlobalDI.instance.profileUserElmStoreFactory.provide()

    override fun initErrorRepeat() {
        binding.errorContent.tvRepeat.setOnClickListener {
            store.accept(Event.Ui.LoadUser(userId))
        }
    }

    companion object {
        const val USER_ID_KEY = "UserId"
        fun newInstance(userId: Int): UserProfileFragment {
            val args = Bundle()
            args.putInt(USER_ID_KEY, userId)
            val fragment = UserProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
