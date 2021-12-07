package com.homework.coursework.presentation.ui.authorization

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.homework.coursework.databinding.LoginFragmentBinding
import com.homework.coursework.presentation.interfaces.BottomNavigationController
import com.homework.coursework.presentation.interfaces.NavigateController
import com.homework.coursework.presentation.ui.authorization.elm.Effect
import com.homework.coursework.presentation.ui.authorization.elm.Event
import com.homework.coursework.presentation.ui.authorization.elm.State
import vivid.money.elmslie.android.base.ElmFragment

abstract class AuthBaseFragment : ElmFragment<Event, Effect, State>() {

    private var _binding: LoginFragmentBinding? = null
    protected val binding get() = _binding!!
    private var bottomNavigationController: BottomNavigationController? = null
    internal var navigateController: NavigateController? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomNavigationController) {
            bottomNavigationController = context
            bottomNavigationController?.goneBottomNavigation()
        }
        if (context is NavigateController) {
            navigateController = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
    }

    private fun initButton() {
        binding.btnLogin.setOnClickListener {
            authAction()
        }
    }

    internal abstract fun authAction()

    override fun onDetach() {
        super.onDetach()
        bottomNavigationController = null
        navigateController = null
    }
}
