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
) : Parcelable


sealed class Event {

    sealed class Ui : Event() {
        object LoadMe: Ui()
        class LoadUser(userId: Int): Ui()
    }

    sealed class Internal : Event() {

        data class MeLoaded(val item: UserItem) : Internal()

        data class ErrorLoading(val error: Throwable) : Internal()
    }
}

sealed class Effect {
    data class UserLoadError(val error: Throwable) : Effect()
}

sealed class Command {
    object LoadMe : Command()
}
