package com.homework.coursework.presentation.ui.stream.elm

import android.os.Parcelable
import com.homework.coursework.presentation.adapter.data.StreamItem
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class State(
    val itemList: @RawValue List<StreamItem> = emptyList(),
    val error: Throwable? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isUpdate: Boolean = false
) : Parcelable

sealed class Event {

    sealed class Ui : Event() {
        object LoadAllStreams : Ui()
        object LoadSubscribedStreams : Ui()
        class SearchStreams(val query: String) : Ui()
        class SearchSubscribedStreams(val query: String) : Ui()
        object OnStop : Ui()
    }

    sealed class Internal : Event() {
        class StreamLoaded(val itemList: List<StreamItem>) : Internal()
        class ErrorLoading(val error: Throwable) : Internal()
    }
}

sealed class Effect {
    class StreamLoadError(val error: Throwable) : Effect()
}

sealed class Command {
    object LoadAllStreams : Command()
    object LoadSubscribedStreams : Command()
    class SearchStreams(val query: String) : Command()
    class SearchSubscribedStreams(val query: String) : Command()
}
