package com.homework.coursework.presentation.stream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.homework.coursework.databinding.StreamViewPageFragmentBinding
import com.homework.coursework.di.GlobalDI
import com.homework.coursework.presentation.adapter.StreamNameAdapter
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.interfaces.TopicItemCallback
import com.homework.coursework.presentation.stream.StreamFragment.Companion.KEY_QUERY_DATA
import com.homework.coursework.presentation.stream.StreamFragment.Companion.KEY_QUERY_REQUEST
import com.homework.coursework.presentation.stream.elm.Effect
import com.homework.coursework.presentation.stream.elm.Event
import com.homework.coursework.presentation.stream.elm.State
import com.homework.coursework.presentation.utils.off
import kotlinx.serialization.ExperimentalSerializationApi
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.store.Store

@ExperimentalSerializationApi
class StreamListFragment : ElmFragment<Event, Effect, State>(), TopicItemCallback {
    private lateinit var streamAdapter: StreamNameAdapter
    private var tabState: Int = INIT_VALUE
    private var _binding: StreamViewPageFragmentBinding? = null
    private var currQuery: String = ""
    private val binding
        get() = _binding
            ?: throw IllegalStateException(
                "Cannot access view in after view destroyed and before view creation"
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        getArgument()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StreamViewPageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener(
            "$KEY_QUERY_REQUEST$tabState"
        ) { _, bundle ->
            currQuery = bundle.getString(KEY_QUERY_DATA) ?: ""
            store.accept(getNeedEvent(currQuery))
        }
        initErrorRepeat()
        initRecycler()
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
            rvStreams.visibility = View.GONE
            shStreams.off()
            nsvErrorConnection.visibility = View.VISIBLE
        }
    }

    /**
     * show loading layout
     */
    private fun showLoadingScreen() {
        with(binding) {
            nsvErrorConnection.visibility = View.GONE
            shStreams.startShimmer()
        }
    }

    /**
     * show result screen
     */
    private fun showResultScreen() {
        with(binding) {
            nsvErrorConnection.isVisible = false
            shStreams.off()
            rvStreams.visibility = View.VISIBLE
        }
    }

    /**
     * update channels recycle view
     * @param listStreams is new list of channels for recycle
     */
    private fun dataStreamUpdate(listStreams: List<StreamItem>) {
        binding.rvStreams.visibility = View.VISIBLE
        streamAdapter.submitList(listStreams)
    }

    private fun initRecycler() {
        streamAdapter = StreamNameAdapter().apply {
            setTopicListener(this@StreamListFragment)
        }
        with(binding.rvStreams) {
            val itemDecorator = getDividerItemDecoration()
            addItemDecoration(itemDecorator)
            adapter = streamAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun getArgument() {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            tabState = getInt(ARG_OBJECT)
        }
    }

    override fun onTopicItemClick(topic: TopicItem, stream: StreamItem) {
        val bundle = Bundle().apply {
            putParcelable(STREAM_KEY, stream)
            putParcelable(TOPIC_KEY, topic)
        }
        setFragmentResult(REQUEST_KEY_CHOICE, bundle)
    }

    override val initEvent: Event
        get() = getNeedEvent()

    override fun render(state: State) {
        if (state.isLoading) {
            showLoadingScreen()
            return
        }
        if (state.isSecondError) {
            showErrorScreen()
            return
        }
        if (state.itemList.first().errorHandle.isError.not()) {
            showResultScreen()
            dataStreamUpdate(state.itemList)
        }
    }

    private fun getNeedEvent() = if (tabState == SUBSCRIBED_FRAGMENT) {
        Event.Ui.LoadSubscribedStreams
    } else {
        Event.Ui.LoadAllStreams
    }

    private fun getNeedEvent(query: String) = if (tabState == SUBSCRIBED_FRAGMENT) {
        Event.Ui.SearchSubscribedStreams(query)
    } else {
        Event.Ui.SearchStreams(query)
    }

    override fun createStore(): Store<Event, Effect, State> = getNeedStore()

    private fun getNeedStore() = if (tabState == SUBSCRIBED_FRAGMENT) {
        GlobalDI.instance.allStreamStoreFactory.provide()
    } else {
        GlobalDI.instance.subscribedStreamStoreFactory.provide()
    }

    companion object {
        const val ARG_OBJECT = "object"
        const val REQUEST_KEY_CHOICE = "requestKeyChoice"
        const val STREAM_KEY = "stream"
        const val TOPIC_KEY = "topic"
        const val INIT_VALUE = -1
        const val SUBSCRIBED_FRAGMENT = 0
    }
}
