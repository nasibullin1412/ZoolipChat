package com.homework.coursework.presentation.ui.profile.main

import android.content.Context
import android.os.Bundle
import android.view.View
import com.homework.coursework.di.UserStore
import com.homework.coursework.presentation.App
import com.homework.coursework.presentation.interfaces.BottomNavigationController
import com.homework.coursework.presentation.ui.profile.BaseProfileFragment
import com.homework.coursework.presentation.ui.profile.elm.Effect
import com.homework.coursework.presentation.ui.profile.elm.Event
import com.homework.coursework.presentation.ui.profile.elm.State
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class UserProfileFragment : BaseProfileFragment() {

    @Inject
    @UserStore
    internal lateinit var userProfileStore: Store<Event, Effect, State>

    private var userId = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomNavigationController) {
            bottomNavigationController = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.userProfileComponent().inject(this)
        userId = requireArguments().getInt(USER_ID_KEY, 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogout.visibility = View.GONE
        bottomNavigationController?.goneBottomNavigation()
    }

    override val initEvent: Event
        get() = Event.Ui.LoadUser(userId)

    override fun createStore(): Store<Event, Effect, State> = userProfileStore

    override fun initErrorRepeat() {
        binding.errorContent.tvRepeat.setOnClickListener {
            store.accept(Event.Ui.LoadUser(userId))
        }
    }

    override fun onDetach() {
        super.onDetach()
        bottomNavigationController = null
    }

    companion object {
        const val USER_ID_KEY = "UserId"

        fun createBundle(userId: Int): Bundle {
            return Bundle().apply { putInt(USER_ID_KEY, userId) }
        }

        fun newInstance(args: Bundle): UserProfileFragment {
            val fragment = UserProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
