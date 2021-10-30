package com.homework.coursework.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.homework.coursework.databinding.ProfileFragmentBinding
import com.homework.coursework.presentation.MainActivity.Companion.CURR_USER_AVATAR_URL
import com.homework.coursework.presentation.MainActivity.Companion.CURR_USER_NAME
import com.homework.coursework.presentation.MainActivity.Companion.CURR_USER_STATE
import com.homework.coursework.presentation.MainActivity.Companion.CURR_USER_STATUS

class ProfileFragment : Fragment() {

    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

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
        initView()
    }

    private fun initView() {
        with(binding) {
            imgProfile.load(CURR_USER_AVATAR_URL)
            tvName.text = CURR_USER_NAME
            tvStatus.text = CURR_USER_STATUS
            tvState.text = CURR_USER_STATE
        }
    }
}
