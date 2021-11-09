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
                binding.shStreams.stopShimmer()
                binding.shStreams.hideShimmer()
                binding.shStreams.visibility = View.GONE
                binding.clResult.visibility = View.GONE
                binding.nsvErrorConnection.visibility = View.VISIBLE
                showToast(stateScreen.error.message)
            }
            ProfileScreenState.Loading -> {
                binding.clResult.visibility = View.GONE
                binding.nsvErrorConnection.visibility = View.GONE
                binding.shStreams.startShimmer()
            }
            is ProfileScreenState.Result -> {
                binding.nsvErrorConnection.visibility = View.GONE
                binding.shStreams.stopShimmer()
                binding.shStreams.hideShimmer()
                binding.shStreams.visibility = View.GONE
                binding.clResult.visibility = View.VISIBLE
                updateView(stateScreen.userItem)
            }
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
