package com.homework.coursework.presentation.profile.elm

import com.homework.coursework.presentation.interfaces.TwoSourceHandleReducer
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class Reducer : DslReducer<Event, State, Effect, Command>(),
    TwoSourceHandleReducer<Event.Internal.MeLoaded, State> {

    override var isSecondError = false

    override fun Result.reduce(event: Event) = when (event) {
        is Event.Internal.ErrorLoading -> {
            effects { +Effect.UserLoadError(event.error) }
            state { copy(error = event.error, isLoading = false) }
        }
        Event.Ui.LoadMe -> {
            state { copy(isLoading = true, error = null) }
            commands { +Command.LoadMe }
        }
        is Event.Internal.MeLoaded -> {
            if (isWithError(event)) {
                handleResult(event)?.let { state { it } }
                effects { +Effect.UserLoadError(event.item.errorHandle.error) }
            } else {
                state {
                    copy(
                        item = event.item,
                        isLoading = false,
                        error = null,
                        isSecondError = false
                    )
                }
            }
        }
        is Event.Ui.LoadUser -> {
            throw NotImplementedError()
        }
        Event.Ui.OnStop -> {
            isSecondError = false
        }
    }

    override fun handleResult(event: Event.Internal.MeLoaded): State? {
        return if (isSecondError) {
            State(
                item = event.item,
                isLoading = false,
                isSecondError = isSecondErrorChange(),
                error = event.item.errorHandle.error
            )
        } else {
            isSecondErrorChange()
            null
        }
    }

    override fun isWithError(event: Event.Internal.MeLoaded): Boolean {
        return event.item.errorHandle.isError
    }
}
