package com.homework.coursework.presentation.stream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.homework.coursework.R
import com.homework.coursework.databinding.ChannelViewPageFragmentBinding
import com.homework.coursework.presentation.adapter.StreamNameAdapter
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.interfaces.TopicItemCallback

class StreamListFragment : Fragment(), TopicItemCallback {
    private lateinit var channelAdapter: StreamNameAdapter
    private val viewModel: StreamViewModel by viewModels()
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
        _binding = ChannelViewPageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgument()
        initRecycler()
        initObserver()
        getStreamData()
    }

    private fun getStreamData() {
        when (requireArguments().getInt(ARG_OBJECT)) {
            GetStreamType.ALL_STREAMS.value -> {
                viewModel.getAllStreams()
            }
            GetStreamType.SUBSCRIBED_STREAMS.value -> {
                viewModel.getSubscribedStream()
            }
        }
    }

    private fun initObserver() {
        viewModel.streamScreenState.observe(viewLifecycleOwner) { processMainScreenState(it) }
    }

    private fun processMainScreenState(state: StreamScreenState?) {
        when (state) {
            is StreamScreenState.Error -> {

            }
            is StreamScreenState.Loading -> {

            }
            is StreamScreenState.Result -> {
                dataUpdate(state.items)
            }
        }
    }

    /**
     * update channels recycle view
     * @param listStreams is new list of channels for recycle
     */
    private fun dataUpdate(listStreams: List<StreamItem>) {
        binding.rvSpinner.visibility = View.VISIBLE
        channelAdapter.submitList(listStreams)
    }

    private fun initRecycler() {
        channelAdapter = StreamNameAdapter().apply {
            setListener(this@StreamListFragment)
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
            adapter = channelAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun getArgument() {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            isInAction = getInt(ARG_OBJECT) == SEARCH_IN_ACTION
        }
    }

    override fun onTopicItemClick(idTopic: Int, idChannel: Int) {
        val bundle = Bundle()
        bundle.putInt(CHANNEL_KEY, idChannel)
        bundle.putInt(TOPIC_KEY, idTopic)
        setFragmentResult(REQUEST_KEY, bundle)
    }

    companion object {
        const val ARG_OBJECT = "object"
        const val SEARCH_IN_ACTION = 0
        const val REQUEST_KEY = "requestKey"
        const val CHANNEL_KEY = "channelId"
        const val TOPIC_KEY = "topicId"
    }
}
