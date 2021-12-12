package com.homework.coursework.presentation.ui.chat.main

import android.os.Bundle
import android.view.View
import com.homework.coursework.di.StreamChatStore
import com.homework.coursework.presentation.App
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.ui.chat.ChatBaseFragment
import com.homework.coursework.presentation.ui.chat.elm.Effect
import com.homework.coursework.presentation.ui.chat.elm.Event
import com.homework.coursework.presentation.ui.chat.elm.State
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class StreamChatFragment : ChatBaseFragment() {

    @Inject
    @StreamChatStore
    internal lateinit var streamChatStore: Store<Event, Effect, State>

    override fun initArguments() {
        currentStream = requireArguments().getParcelable(STREAM_KEY)
            ?: throw IllegalArgumentException("stream required")
        currentTopic = TopicItem.createEmptyTopicItem()
    }

    override fun sendButtonAction() {
        store.accept(
            Event.Ui.SendMessage(
                streamItem = currentStream,
                topicItem = currentTopic.copy(topicName = binding.etTopicName.text.toString()),
                content = binding.etMessage.text.toString()
            )
        )
        binding.etMessage.setText("")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.streamChatComponent().inject(this)
    }

    override fun initNames() {
        binding.tvStreamNameBar.text = currentStream.streamName
    }

    override fun configureView() {
        binding.etTopicName.visibility = View.VISIBLE
    }

    override fun createStore(): Store<Event, Effect, State> = streamChatStore

    companion object {

        fun createBundle(stream: StreamItem): Bundle{
            return Bundle().apply {
                putParcelable(STREAM_KEY, stream)
            }
        }

        fun newInstance(args: Bundle): StreamChatFragment {
            val fragment = StreamChatFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
