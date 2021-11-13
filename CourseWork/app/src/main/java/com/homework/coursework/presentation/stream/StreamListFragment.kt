package com.homework.coursework.presentation.stream

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.homework.coursework.R
import com.homework.coursework.databinding.StreamViewPageFragmentBinding
import com.homework.coursework.presentation.adapter.StreamNameAdapter
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.interfaces.TopicItemCallback
import com.homework.coursework.presentation.stream.StreamFragment.Companion.KEY_QUERY_DATA
import com.homework.coursework.presentation.stream.StreamFragment.Companion.KEY_QUERY_REQUEST
import com.homework.coursework.presentation.utils.off
import com.homework.coursework.presentation.utils.showToast

class StreamListFragment : Fragment(), TopicItemCallback {
    private lateinit var streamAdapter: StreamNameAdapter
    private val viewModel: StreamViewModel by viewModels()
    private var tabState: Int = INIT_VALUE
    private var _binding: StreamViewPageFragmentBinding? = null
    private var currQuery: String = ""
    private val binding
        get() = _binding
            ?: throw IllegalStateException(
                "Cannot access view in after view destroyed and before view creation"
            )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("CallbackCheck", "onCreateView")
        _binding = StreamViewPageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgument()
        setFragmentResultListener(
            "$KEY_QUERY_REQUEST$tabState"
        ) { _, bundle ->
            currQuery = bundle.getString(KEY_QUERY_DATA) ?: ""
            viewModel.searchStreams(tabState, currQuery)
        }
        initObservers()
        getStreamData()
        initRecycler()
        Log.d("CallbackCheck", "onViewCreated")
    }

    private fun getStreamData() {
        tabState = requireArguments().getInt(ARG_OBJECT)
        viewModel.getStreams(tabState)
    }

    private fun initObservers() {
        viewModel.streamScreenState.observe(viewLifecycleOwner) { processStreamScreenState(it) }
        binding.errorContent.tvRepeat.setOnClickListener {
            viewModel.getStreams(tabState)
        }
    }

    private fun processStreamScreenState(stateStream: StreamScreenState) {
        when (stateStream) {
            is StreamScreenState.Error -> {
                binding.rvStreams.visibility = View.GONE
                binding.shStreams.off()
                binding.nsvErrorConnection.visibility = View.VISIBLE
                showToast(stateStream.error.message)
            }
            StreamScreenState.Loading -> {
                binding.nsvErrorConnection.visibility = View.GONE
                binding.shStreams.startShimmer()
            }
            is StreamScreenState.Result -> {
                binding.nsvErrorConnection.isVisible = false
                binding.shStreams.off()
                binding.rvStreams.visibility = View.VISIBLE
                dataStreamUpdate(stateStream.data)
            }
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
            val itemDecorator = DividerItemDecoration(
                context,
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

    companion object {
        const val ARG_OBJECT = "object"
        const val REQUEST_KEY_CHOICE = "requestKeyChoice"
        const val STREAM_KEY = "stream"
        const val TOPIC_KEY = "topic"
        const val INIT_VALUE = -1
    }
}
