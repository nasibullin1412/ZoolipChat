package com.homework.coursework.presentation.ui.chat.main

import android.os.Bundle
import android.view.View
import com.homework.coursework.di.StreamChatStore
import com.homework.coursework.presentation.App
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.adapter.data.chat.ChatItem
import com.homework.coursework.presentation.interfaces.TopicNameItemCallback
import com.homework.coursework.presentation.ui.chat.ChatBaseFragment
import com.homework.coursework.presentation.ui.chat.elm.Effect
import com.homework.coursework.presentation.ui.chat.elm.Event
import com.homework.coursework.presentation.ui.chat.elm.State
import com.homework.coursework.presentation.utils.CustomFragmentFactory
import com.homework.coursework.presentation.utils.FragmentTag
import com.homework.coursework.presentation.utils.showToast
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class StreamChatFragment : ChatBaseFragment(), TopicNameItemCallback {

    @Inject
    @StreamChatStore
    internal lateinit var streamChatStore: Store<Event, Effect, State>

    private var isNeedForUpdate = false

    override fun initArguments() {
        currentStream = requireArguments().getParcelable(STREAM_KEY)
            ?: throw IllegalArgumentException("stream required")
        currentTopic = TopicItem.createEmptyTopicItem()
    }

    override fun sendButtonAction() {
        val topicName = binding.etTopicName.text.toString()
        if (topicName.isEmpty()) {
            showToast(EMPTY_TOPIC_NAME)
            return
        }
        store.accept(
            Event.Ui.SendMessage(
                streamItem = currentStream,
                topicItem = currentTopic.copy(topicName = topicName),
                content = binding.etMessage.text.toString()
            )
        )
        binding.etMessage.setText("")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.streamChatComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isNeedLoadFirstPage()
    }

    override fun initNames() {
        binding.tvStreamNameBar.text = currentStream.streamName
    }

    override fun configureView() {
        binding.etTopicName.visibility = View.VISIBLE
        chatAdapter.initTopicListener(this)
    }

    override fun getTopicName(): String {
        val topicName = binding.etTopicName.text.toString()
        if (topicName.isEmpty()){
            showToast(EMPTY_TOPIC_NAME)
        }
        return topicName
    }

    override fun createStore(): Store<Event, Effect, State> = streamChatStore

    /**
     * update recycler view with new message
     * @param newList is list with new MessageData
     */
    override fun updateMessage(newList: List<ChatItem>) {
        chatAdapter.submitList(newList)
    }

    override fun onTopicItemCallback(topicName: String) {
        isNeedForUpdate = true
        navigateController?.navigateFragment(
            CustomFragmentFactory.create(
                FragmentTag.TOPIC_CHAT_FRAGMENT_TAG,
                bundle = TopicChatFragment.createBundle(
                    topic = currentTopic.copy(id = 0, topicName = topicName, numberOfMess = 0),
                    stream = currentStream
                )
            )
        )
    }

    private fun isNeedLoadFirstPage() {
        if (!isNeedForUpdate) return
        isNeedForUpdate = false
        store.accept(
            Event.Ui.LoadFirstPage(
                streamItem = currentStream,
                topicItem = currentTopic,
                currId = currId
            )
        )
    }

    companion object {
        const val EMPTY_TOPIC_NAME = "Добавьте название топика..."

        fun createBundle(stream: StreamItem): Bundle {
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
