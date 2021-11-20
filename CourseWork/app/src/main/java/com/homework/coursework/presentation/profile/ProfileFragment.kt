package com.homework.coursework.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.homework.coursework.R
import com.homework.coursework.databinding.ProfileFragmentBinding
import com.homework.coursework.di.GlobalDI
import com.homework.coursework.domain.entity.StatusEnum
import com.homework.coursework.presentation.adapter.data.UserItem
import com.homework.coursework.presentation.profile.elm.Effect
import com.homework.coursework.presentation.profile.elm.Event
import com.homework.coursework.presentation.profile.elm.State
import com.homework.coursework.presentation.utils.getColor
import com.homework.coursework.presentation.utils.off
import com.homework.coursework.presentation.utils.showToast
import kotlinx.serialization.ExperimentalSerializationApi
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.store.Store

@ExperimentalSerializationApi
class ProfileFragment : ElmFragment<Event, Effect, State>() {

    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!
    private var userId = DEFAULT_VALUE

    override fun onCreate(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            userId = savedInstanceState.getInt(USER_ID_KEY, DEFAULT_VALUE)
        }
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initErrorRepeat()
    }

    private fun initErrorRepeat() {
        binding.errorContent.tvRepeat.setOnClickListener {
            store.accept(getNeedEvent())
        }
    }

    /**
     * show error layout
     */
    private fun showErrorScreen() {
        with(binding) {
            shStreams.off()
            clResult.visibility = View.GONE
            nsvErrorConnection.visibility = View.VISIBLE
        }
    }

    /**
     * show loading layout
     */
    private fun showLoadingScreen() {
        with(binding) {
            clResult.visibility = View.GONE
            nsvErrorConnection.visibility = View.GONE
            shStreams.startShimmer()
        }
    }

    /**
     * show result screen
     */
    private fun showResultScreen() {
        with(binding) {
            nsvErrorConnection.visibility = View.GONE
            shStreams.off()
            clResult.visibility = View.VISIBLE
        }
    }

    private fun updateView(userItem: UserItem) {
        with(binding) {
            with(userItem) {
                imgProfile.load(userItem.avatarUrl)
                tvName.text = name
                tvState.text = getStatusString(userItem.userStatus?.status)
                tvState.setTextColor(getColor(getStatusColor(userItem.userStatus?.status)))
            }
        }
    }

    private fun getStatusColor(status: StatusEnum?) = when (status) {
        StatusEnum.ACTIVE -> R.color.state_active_color
        StatusEnum.IDLE -> R.color.state_idle_color
        StatusEnum.OFFLINE -> R.color.state_offline_color
        StatusEnum.UNKNOWN -> R.color.state_offline_color
        null -> throw IllegalArgumentException("status null")
    }

    private fun getStatusString(status: StatusEnum?) = when (status) {
        StatusEnum.ACTIVE -> "active"
        StatusEnum.IDLE -> "idle"
        StatusEnum.OFFLINE -> "offline"
        StatusEnum.UNKNOWN -> "unknown"
        null -> throw IllegalArgumentException("status null")
    }

    override val initEvent: Event
        get() = getNeedEvent()

    override fun createStore(): Store<Event, Effect, State> =
        GlobalDI.instance.elmStoreFactory.provide()

    override fun render(state: State) {
        if (state.isLoading) {
            showLoadingScreen()
            return
        }
        state.error?.let {
            showErrorScreen()
            showToast(state.error.message)
            return
        }
        showResultScreen()
        if (state.item is UserItem) {
            updateView(state.item)
        }
    }

    private fun getNeedEvent() = if (userId == DEFAULT_VALUE) {
        Event.Ui.LoadMe
    } else {
        Event.Ui.LoadUser(userId)
    }

    companion object {
        const val USER_ID_KEY = "UserId"
        const val DEFAULT_VALUE = -1
    }
}
