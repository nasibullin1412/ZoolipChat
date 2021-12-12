package com.homework.coursework.presentation.ui.stream.main

import android.os.Bundle
import androidx.fragment.app.setFragmentResultListener
import com.homework.coursework.di.SubscribedStreams
import com.homework.coursework.presentation.App
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.ui.stream.StreamFragment.Companion.KEY_QUERY_DATA
import com.homework.coursework.presentation.ui.stream.StreamFragment.Companion.KEY_QUERY_REQUEST
import com.homework.coursework.presentation.ui.stream.StreamItemBaseFragment
import com.homework.coursework.presentation.ui.stream.elm.Effect
import com.homework.coursework.presentation.ui.stream.elm.Event
import com.homework.coursework.presentation.ui.stream.elm.State
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class StreamSubscribedFragment : StreamItemBaseFragment() {

    @Inject
    @SubscribedStreams
    internal lateinit var subscribedStreamsStore: Store<Event, Effect, State>

    override val tabState: Int
        get() = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.subscribedStreamsComponent().inject(this)
    }

    override fun onStreamItemLongClick(streamItem: StreamItem) {
        clickAction(topic = TopicItem.createEmptyTopicItem(), stream = streamItem)
    }

    override fun initErrorRepeat() {
        binding.errorContent.tvRepeat.setOnClickListener {
            store.accept(Event.Ui.LoadSubscribedStreams)
        }
    }

    override fun setFragmentResultListener(savedInstanceState: Bundle?) {
        setFragmentResultListener(
            "$KEY_QUERY_REQUEST$tabState"
        ) { _, bundle ->
            currQuery = bundle.getString(KEY_QUERY_DATA) ?: ""
            store.accept(getNeedEvent(currQuery))
        }
    }

    override val initEvent: Event
        get() = Event.Ui.LoadSubscribedStreams


    private fun getNeedEvent(query: String? = null) = if (query != null) {
        Event.Ui.SearchSubscribedStreams(query)
    } else {
        Event.Ui.LoadSubscribedStreams
    }

    override fun createStore(): Store<Event, Effect, State> = subscribedStreamsStore
}
