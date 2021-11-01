package com.homework.coursework.presentation.stream

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.homework.coursework.R
import com.homework.coursework.databinding.ChannelViewPageFragmentBinding
import com.homework.coursework.presentation.adapter.StreamNameAdapter
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.interfaces.StreamItemCallback
import com.homework.coursework.presentation.interfaces.TopicItemCallback
import com.homework.coursework.presentation.stream.StreamFragment.Companion.KEY_CURRENT_POSITION
import com.homework.coursework.presentation.stream.StreamFragment.Companion.KEY_QUERY_DATA
import com.homework.coursework.presentation.stream.StreamFragment.Companion.KEY_QUERY_REQUEST

class StreamListFragment : Fragment(), StreamItemCallback, TopicItemCallback {
    private lateinit var streamAdapter: StreamNameAdapter
    private val viewModel: StreamViewModel by viewModels()
    private var tabState: Int = INIT_VALUE
    private var _binding: ChannelViewPageFragmentBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException(
                "Cannot access view in after view destroyed and before view creation"
            )
    private var isInAction = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("CallbackCheck", "onCreateView")
        _binding = ChannelViewPageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        getStreamData()
        getArgument()
        initRecycler()
        Log.d("CallbackCheck", "onViewCreated")
        setFragmentResultListener(
            KEY_QUERY_REQUEST
        ) { _, bundle ->
            val query = bundle.getString(KEY_QUERY_DATA) ?: ""
            tabState = bundle.getInt(KEY_CURRENT_POSITION)
            viewModel.searchStreams(tabState, query)
        }
    }

    private fun getStreamData() {
        tabState = requireArguments().getInt(ARG_OBJECT)
        viewModel.getStreams(tabState)
    }

    private fun initObservers() {
        viewModel.streamScreenState.observe(viewLifecycleOwner) { processStreamScreenState(it) }
        viewModel.topicScreenState.observe(viewLifecycleOwner) { processTopicScreenState(it) }
    }

    private fun processStreamScreenState(stateStream: StreamScreenState) {
        when (stateStream) {
            is StreamScreenState.Error -> {

            }
            is StreamScreenState.Loading -> {

            }
            is StreamScreenState.Result -> {
                dataStreamUpdate(stateStream.data)
            }
        }
    }

    private fun processTopicScreenState(stateStream: TopicScreenState) {
        when (stateStream) {
            is TopicScreenState.Error -> {

            }
            is TopicScreenState.Loading -> {

            }
            is TopicScreenState.Result -> {
                dataTopicUpdate(stateStream.data, stateStream.position)
            }
        }
    }



    private fun dataTopicUpdate(topics: List<TopicItem>, id: Int) {
        val position = streamAdapter.currentList.indexOfFirst { it.id == id}
        streamAdapter.currentList[position].topicItemList = ArrayList(topics)
        streamAdapter.notifyItemChanged(position)
    }

    /**
     * update channels recycle view
     * @param listStreams is new list of channels for recycle
     */
    private fun dataStreamUpdate(listStreams: List<StreamItem>) {
        binding.rvSpinner.visibility = View.VISIBLE
        streamAdapter.submitList(listStreams)
    }

    private fun initRecycler() {
        streamAdapter = StreamNameAdapter().apply {
            setTopicListener(this@StreamListFragment)
            setStreamListener(this@StreamListFragment)
        }
        with(binding.rvSpinner) {
            val itemDecorator = DividerItemDecoration(
                binding.rvSpinner.context,
                DividerItemDecoration.VERTICAL
            ).apply {
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.sh_item_border,
                    context.theme
                )?.let { setDrawable(it) }
            }
            addItemDecoration(itemDecorator)
            adapter = streamAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun getArgument() {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            isInAction = getInt(ARG_OBJECT) == SEARCH_IN_ACTION
        }
    }

    override fun onTopicItemClick(topic: TopicItem, stream: StreamItem) {
        val bundle = Bundle().apply {
            putParcelable(STREAM_KEY, stream)
            putParcelable(TOPIC_KEY, topic)
        }
        setFragmentResult(REQUEST_KEY_CHOICE, bundle)
    }

    override fun onStreamItemCallback(position: Int) {
        viewModel.getTopics(position)
    }

    companion object {
        const val ARG_OBJECT = "object"
        const val SEARCH_IN_ACTION = 0
        const val REQUEST_KEY_CHOICE = "requestKeyChoice"
        const val STREAM_KEY = "stream"
        const val TOPIC_KEY = "topic"
        const val INIT_VALUE = -1
    }
}
