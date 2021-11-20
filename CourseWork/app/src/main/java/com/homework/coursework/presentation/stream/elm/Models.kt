package com.homework.coursework.presentation.stream.elm

import android.os.Parcelable
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.UserItem
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class State(
    val itemList: @RawValue List<Any> = emptyList(),
    val error: Throwable? = null,
    val isLoading: Boolean = false,
) : Parcelable


sealed class Event {

    sealed class Ui : Event() {
        object LoadAllStreams : Ui()
        object LoadSubscribedStreams: Ui()
        class SearchStreams(query: String): Ui()
        class SearchSubscribedStreams(query: String): Ui()
    }

    sealed class Internal : Event() {
        data class StreamLoaded(val data: List<StreamItem>) : Internal()

        data class ErrorLoading(val error: Throwable) : Internal()
    }
}

sealed class Command {
    object LoadMe : Command()
}
