package com.homework.coursework.presentation.profile.elm

import android.os.Parcelable
import com.homework.coursework.presentation.adapter.data.UserItem
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class State(
    val item: @RawValue Any = Any(),
    val error: Throwable? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isUpdate: Boolean = false
) : Parcelable

sealed class Event {

    sealed class Ui : Event() {
        object LoadMe : Ui()
        object OnStop : Ui()
        data class LoadUser(val userId: Int) : Ui()
    }

    sealed class Internal : Event() {

        data class UserLoaded(val item: UserItem) : Internal()

        data class ErrorLoading(val error: Throwable) : Internal()
    }
}

sealed class Effect {
    data class UserLoadError(val error: Throwable) : Effect()
}

sealed class Command {
    object LoadMe : Command()
    data class LoadUser(val id: Int) : Command()
}
