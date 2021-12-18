package com.homework.coursework.presentation.ui.editmessage.elm

import android.os.Parcelable
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.adapter.data.chat.MessageItem
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
        class EditMessage(val messageItem: MessageItem) : Ui()
        class InitView(val messageItem: MessageItem) : Ui()
    }

    sealed class Internal : Event() {
        object SuccessEditMessage : Internal()
        class ErrorEdit(val error: Throwable) : Internal()
    }
}

sealed class Effect {
    object SuccessEdit : Effect()
    class ErrorEdit(val error: Throwable) : Effect()
}

sealed class Command {
    class EditMessage(val messageData: MessageItem) : Command()
}
