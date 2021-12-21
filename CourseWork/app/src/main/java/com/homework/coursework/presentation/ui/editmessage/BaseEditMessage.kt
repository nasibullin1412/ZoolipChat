package com.homework.coursework.presentation.ui.editmessage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.homework.coursework.databinding.FragmentEditMessageBinding
import com.homework.coursework.presentation.interfaces.BottomNavigationController
import com.homework.coursework.presentation.ui.editmessage.elm.Effect
import com.homework.coursework.presentation.ui.editmessage.elm.Event
import com.homework.coursework.presentation.ui.editmessage.elm.State
import vivid.money.elmslie.android.base.ElmFragment

abstract class BaseEditMessage : ElmFragment<Event, Effect, State>() {
    private var _binding: FragmentEditMessageBinding? = null
    protected val binding get() = _binding!!
    private var bottomNavigationController: BottomNavigationController? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomNavigationController) {
            bottomNavigationController = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEditMessageButton()
        initBackButton()
        bottomNavigationController?.goneBottomNavigation()
    }

    abstract fun initArguments()

    abstract fun initBackButton()

    abstract fun initEditMessageButton()

    override fun onDetach() {
        super.onDetach()
        bottomNavigationController = null
    }
}
