package com.homework.coursework.presentation.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.homework.coursework.databinding.PeopleFragmentBinding
import com.homework.coursework.presentation.adapter.PeopleAdapter
import com.homework.coursework.presentation.adapter.data.UserItem
import com.homework.coursework.presentation.interfaces.UserItemCallback
import com.homework.coursework.presentation.people.elm.Effect
import com.homework.coursework.presentation.people.elm.Event
import com.homework.coursework.presentation.people.elm.State
import com.homework.coursework.presentation.utils.off
import vivid.money.elmslie.android.base.ElmFragment
import javax.inject.Inject

abstract class PeopleBaseFragment : ElmFragment<Event, Effect, State>(), UserItemCallback {

    @Inject
    internal lateinit var peopleAdapter: PeopleAdapter
    protected var currQuery: String = ""
    private var _binding: PeopleFragmentBinding? = null

    protected val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PeopleFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initErrorRepeat()
        initRecycler()
        initSearch()
    }

    override fun render(state: State) {
        if (state.isLoading) {
            showLoadingScreen()
            return
        }
        if (state.isError) {
            showErrorScreen()
            return
        }
        if (state.isUpdate) {
            showResultScreen()
            dataUserUpdate(state.itemList)
        }
    }

    override fun onStop() {
        super.onStop()
        store.accept(Event.Ui.OnStop)
    }

    override fun onUserItemClick(id: Int) {
    }

    abstract fun initErrorRepeat()

    abstract fun initSearch()

    /**
     * show error layout
     */
    private fun showErrorScreen() {
        with(binding) {
            rvUsers.visibility = View.GONE
            shPeople.off()
            nsvErrorConnection.visibility = View.VISIBLE
        }
    }

    /**
     * show loading layout
     */
    private fun showLoadingScreen() {
        with(binding) {
            nsvErrorConnection.visibility = View.GONE
            shPeople.startShimmer()
        }
    }

    /**
     * show result screen
     */
    private fun showResultScreen() {
        with(binding) {
            nsvErrorConnection.isVisible = false
            shPeople.off()
            rvUsers.visibility = View.VISIBLE
        }
    }

    /**
     * update channels recycle view
     * @param listPeople is new list of channels for recycle
     */
    private fun dataUserUpdate(listPeople: List<UserItem>) {
        binding.rvUsers.visibility = View.VISIBLE
        peopleAdapter.submitList(listPeople)
    }

    private fun initRecycler() {
        with(binding.rvUsers) {
            adapter = peopleAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}
