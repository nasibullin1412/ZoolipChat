package com.homework.coursework.presentation.ui.chat.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.homework.coursework.di.TopicChatStore
import com.homework.coursework.presentation.App
import com.homework.coursework.presentation.adapter.data.chat.MessageItem
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.ui.chat.ChatBaseFragment
import com.homework.coursework.presentation.ui.chat.elm.Effect
import com.homework.coursework.presentation.ui.chat.elm.Event
import com.homework.coursework.presentation.ui.chat.elm.State
import com.homework.coursework.presentation.utils.getValueByCondition
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class TopicChatFragment : ChatBaseFragment() {

    @Inject
    @TopicChatStore
    internal lateinit var topicChatStore: Store<Event, Effect, State>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.topicChatComponent().inject(this)
    }

    override fun initArguments() {
        currentTopic = requireArguments().getParcelable(TOPIC_KEY)
            ?: throw IllegalArgumentException("topic required")
        currentStream = requireArguments().getParcelable(STREAM_KEY)
            ?: throw IllegalArgumentException("stream required")
    }

    @SuppressLint("SetTextI18n")
    override fun initNames() {
        with(binding) {
            tvStreamNameBar.text = currentStream.streamName
            tvTopicName.text = "Topic: ${currentTopic.topicName}"
        }
    }

    override fun configureView() {
        binding.tvTopicName.visibility = View.VISIBLE
    }

    override fun sendButtonAction() {
        store.accept(
            Event.Ui.SendMessage(
                streamItem = currentStream,
                topicItem = currentTopic,
                content = binding.etMessage.text.toString()
            )
        )
        binding.etMessage.setText("")
    }

    override fun createStore(): Store<Event, Effect, State> = topicChatStore

    override fun onStop() {
        super.onStop()
        val numberOfMessage = chatAdapter.currentList.filterIsInstance<MessageItem>().size
        val last = DATABASE_MESSAGE_THRESHOLD.getValueByCondition(
            condition = DATABASE_MESSAGE_THRESHOLD < numberOfMessage,
            second = numberOfMessage
        )
        store.accept(
            Event.Ui.SaveMessage(
                streamItem = currentStream,
                topicItem = currentTopic,
                messages = chatAdapter.currentList.takeLast(last),
                currId = currId
            )
        )
        bottomNavigationController?.visibleBottomNavigation()
    }

    companion object {
        const val TOPIC_KEY = "topic"

        fun createBundle(topic: TopicItem, stream: StreamItem): Bundle{
            return Bundle().apply {
                putParcelable(TOPIC_KEY, topic)
                putParcelable(STREAM_KEY, stream)
            }
        }

        fun newInstance(args: Bundle): TopicChatFragment {
            val fragment = TopicChatFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
