package com.homework.coursework.presentation.ui.createstream.elm

import android.os.Parcelable
import com.homework.coursework.domain.entity.SubscribeData
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
        class SubscribeToStream(val subscribeData: SubscribeData) : Ui()
    }

    sealed class Internal : Event() {
        class SuccessSubscribe(val data: String) : Internal()
        class ErrorSubscribe(val error: Throwable) : Internal()
    }
}

sealed class Effect {
    class SuccessSubscribe(val data: String) : Effect()
    class ErrorSubscribe(val error: Throwable) : Effect()
}

sealed class Command {
    class SubscribeToStream(val subscribeData: SubscribeData) : Command()
}
