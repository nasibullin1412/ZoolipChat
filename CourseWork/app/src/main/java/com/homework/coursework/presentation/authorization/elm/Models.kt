package com.homework.coursework.presentation.authorization.elm

import android.os.Parcelable
import com.homework.coursework.domain.entity.UserData
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class State(
    val apiToken: @RawValue Any = Any(),
    val error: Throwable? = null,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
) : Parcelable


sealed class Event {

    sealed class Ui : Event() {
        data class PressButton(val login: String, val password: String) : Ui()
        object GetMe : Ui()
        object CheckDatabase : Ui()
    }

    sealed class Internal : Event() {
        data class SuccessGetMe(val user: UserData) : Internal()
        data class SuccessAuth(val apiToken: String) : Internal()
        data class ErrorAuth(val throwable: Throwable) : Internal()
    }
}

sealed class Effect {
    object SuccessAuth : Effect()
    object SuccessGetApiToken : Effect()
    data class ErrorAuth(val error: Throwable) : Effect()
}

sealed class Command {
    data class AuthUser(val login: String, val password: String) : Command()
    object CheckIsAuth : Command()
    object GetMe : Command()
}
