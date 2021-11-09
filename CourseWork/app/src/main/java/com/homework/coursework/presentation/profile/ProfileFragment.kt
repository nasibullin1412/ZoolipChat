package com.homework.coursework.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.homework.coursework.R
import com.homework.coursework.databinding.ProfileFragmentBinding
import com.homework.coursework.domain.entity.StatusEnum
import com.homework.coursework.presentation.adapter.data.UserItem
import com.homework.coursework.presentation.frameworks.network.utils.NetworkConstants.USER_ID
import com.homework.coursework.presentation.utils.getColor
import com.homework.coursework.presentation.utils.off
import com.homework.coursework.presentation.utils.showToast

class ProfileFragment : Fragment() {

    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        initObservers()
        viewModel.getUserData(UseCaseType.GET_ME_PROFILE, USER_ID)
    }

    private fun initObservers() {
        viewModel.profileScreenState.observe(viewLifecycleOwner, { processStreamScreenState(it) })
        binding.errorContent.tvRepeat.setOnClickListener {
            viewModel.getUserData(UseCaseType.GET_ME_PROFILE, USER_ID)
        }
    }

    private fun processStreamScreenState(stateScreen: ProfileScreenState) {
        when (stateScreen) {
            is ProfileScreenState.Error -> {
                showErrorScreen()
                showToast(stateScreen.error.message)
            }
            ProfileScreenState.Loading -> {
                showLoadingScreen()
            }
            is ProfileScreenState.Result -> {
                showResultScreen()
                updateView(stateScreen.userItem)
            }
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
        null -> throw IllegalArgumentException("status null")
    }

    private fun getStatusString(status: StatusEnum?) = when (status) {
        StatusEnum.ACTIVE -> "active"
        StatusEnum.IDLE -> "idle"
        StatusEnum.OFFLINE -> "offline"
        null -> throw IllegalArgumentException("status null")
    }
}
