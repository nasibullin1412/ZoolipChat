package com.homework.coursework.presentation.ui.editmessage.elm

import com.homework.coursework.di.EditMessageFragmentScope
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject

@EditMessageFragmentScope
class EditMessageReducer @Inject constructor() : DslReducer<Event, State, Effect, Command>() {
    override fun Result.reduce(event: Event) = when (event) {
        is Event.Internal.ErrorEdit -> {
            effects { +Effect.ErrorEdit(event.error) }
        }
        Event.Internal.SuccessEditMessage -> {
            effects { +Effect.SuccessEdit }
        }
        is Event.Ui.EditMessage -> {
            commands { +Command.EditMessage(messageData = event.messageItem) }
        }
        is Event.Ui.InitView -> {
            state { copy(item = event.messageItem, isUpdate = true) }
        }
    }
}
