package com.homework.coursework.presentation.ui.stream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.homework.coursework.databinding.StreamViewPageFragmentBinding
import com.homework.coursework.presentation.adapter.StreamNameAdapter
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.interfaces.StreamItemCallback
import com.homework.coursework.presentation.interfaces.TopicItemCallback
import com.homework.coursework.presentation.ui.chat.ChatBaseFragment.Companion.STREAM_KEY
import com.homework.coursework.presentation.ui.chat.main.TopicChatFragment.Companion.TOPIC_KEY
import com.homework.coursework.presentation.ui.stream.StreamFragment.Companion.CREATE_STREAM_REQUEST
import com.homework.coursework.presentation.ui.stream.elm.Effect
import com.homework.coursework.presentation.ui.stream.elm.Event
import com.homework.coursework.presentation.ui.stream.elm.State
import com.homework.coursework.presentation.utils.off
import vivid.money.elmslie.android.base.ElmFragment
import javax.inject.Inject

abstract class StreamItemBaseFragment : ElmFragment<Event, Effect, State>(), TopicItemCallback,
    StreamItemCallback {

    @Inject
    internal lateinit var streamAdapter: StreamNameAdapter
    protected var currQuery: String = ""
    protected var isStreamCreated = false
    private var _binding: StreamViewPageFragmentBinding? = null

    protected val binding
        get() = _binding!!

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
        setFragmentResultListener()
        setCreateStreamListener()
        initErrorRepeat()
        initRecycler()
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
            dataStreamUpdate(state.itemList)
        }
    }

    override fun onStop() {
        super.onStop()
        store.accept(Event.Ui.OnStop)
    }

    override fun onTopicItemClick(topic: TopicItem, stream: StreamItem) {
        clickAction(topic, stream)
    }

    protected fun clickAction(topic: TopicItem, stream: StreamItem) {
        val bundle = Bundle().apply {
            putParcelable(STREAM_KEY, stream)
            putParcelable(TOPIC_KEY, topic)
        }
        setFragmentResult(REQUEST_KEY_CHOICE, bundle)
    }

    abstract val tabState: Int

    abstract fun initErrorRepeat()

    abstract fun setFragmentResultListener()

    private fun setCreateStreamListener() {
        setFragmentResultListener(CREATE_STREAM_REQUEST) { _, _ -> isStreamCreated = true }
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
        streamAdapter.apply {
            setTopicListener(this@StreamItemBaseFragment)
            setStreamListener(this@StreamItemBaseFragment)
        }
        with(binding.rvStreams) {
            val itemDecorator = getDividerItemDecoration()
            addItemDecoration(itemDecorator)
            adapter = streamAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        const val REQUEST_KEY_CHOICE = "requestKeyChoice"
    }
}
