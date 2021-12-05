package com.homework.coursework.presentation.people.elm

import android.os.Parcelable
import com.homework.coursework.presentation.adapter.data.UserItem
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class State(
    val itemList: @RawValue List<UserItem> = emptyList(),
    val error: Throwable? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isUpdate: Boolean = false
) : Parcelable

sealed class Event {

    sealed class Ui : Event() {
        object LoadUsers : Ui()
        class SearchUsers(val query: String) : Ui()
        object OnStop : Ui()
    }

    sealed class Internal : Event() {
        data class UserLoaded(val itemList: List<UserItem>) : Internal()

        data class ErrorLoading(val error: Throwable) : Internal()
    }
}

sealed class Effect {
    data class UserLoadedError(val error: Throwable) : Effect()
}

sealed class Command {
    object LoadUsers : Command()
    class SearchUsers(val query: String) : Command()
}