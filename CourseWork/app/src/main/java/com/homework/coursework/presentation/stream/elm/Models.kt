package com.homework.coursework.presentation.stream.elm

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
        data class StreamLoaded(val itemList: List<StreamItem>) : Internal()

        data class ErrorLoading(val error: Throwable) : Internal()
    }
}

sealed class Effect {
    data class StreamLoadError(val error: Throwable) : Effect()
}

sealed class Command {
    object LoadAllStreams : Command()
    object LoadSubscribedStreams : Command()
    data class SearchStreams(val query: String) : Command()
    data class SearchSubscribedStreams(val query: String) : Command()
}
