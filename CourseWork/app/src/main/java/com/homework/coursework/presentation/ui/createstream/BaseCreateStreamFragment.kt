package com.homework.coursework.presentation.ui.createstream

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.homework.coursework.databinding.FragmentCreateNewStreamBinding
import com.homework.coursework.presentation.interfaces.BottomNavigationController
import com.homework.coursework.presentation.ui.createstream.elm.Effect
import com.homework.coursework.presentation.ui.createstream.elm.Event
import com.homework.coursework.presentation.ui.createstream.elm.State
import vivid.money.elmslie.android.base.ElmFragment

abstract class BaseCreateStreamFragment : ElmFragment<Event, Effect, State>() {

    private var _binding: FragmentCreateNewStreamBinding? = null
    protected val binding get() = _binding!!
    private var bottomNavigationController: BottomNavigationController? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomNavigationController) {
            bottomNavigationController = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNewStreamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationController?.goneBottomNavigation()
        initErrorRepeat()
        initCreateStreamButton()
        initBackButton()
    }

    override fun onDetach() {
        super.onDetach()
        bottomNavigationController = null
    }

    abstract fun initBackButton()
    abstract fun initErrorRepeat()
    abstract fun initCreateStreamButton()
}
