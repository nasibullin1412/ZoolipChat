package com.homework.coursework.presentation.stream.main

import android.os.Bundle
import androidx.fragment.app.setFragmentResultListener
import com.homework.coursework.di.GlobalDI
import com.homework.coursework.presentation.stream.StreamFragment.Companion.KEY_QUERY_DATA
import com.homework.coursework.presentation.stream.StreamFragment.Companion.KEY_QUERY_REQUEST
import com.homework.coursework.presentation.stream.StreamItemBaseFragment
import com.homework.coursework.presentation.stream.elm.Effect
import com.homework.coursework.presentation.stream.elm.Event
import com.homework.coursework.presentation.stream.elm.State
import kotlinx.serialization.ExperimentalSerializationApi
import vivid.money.elmslie.core.store.Store

@ExperimentalSerializationApi
class StreamSubscribedFragment : StreamItemBaseFragment() {

    override val tabState: Int
        get() = 0

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

    override fun createStore(): Store<Event, Effect, State> =
        GlobalDI.instance.subscribedStreamStoreFactory.provide()
}
