package com.homework.coursework.presentation.profile.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class Reducer : DslReducer<Event, State, Effect, Command>() {

    private var isSecondError = false

    override fun Result.reduce(event: Event) = when (event) {
        is Event.Internal.ErrorLoading -> {
            state { copy(error = event.error, isLoading = false) }
        }
        Event.Ui.LoadMe -> {
            state { copy(isLoading = true, error = null) }
            commands { +Command.LoadMe }
        }
        is Event.Internal.MeLoaded -> {
            if (event.item.errorHandle.isError) {
                handleResult(event)?.let { state { it } }
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
    }

    private fun handleResult(event: Event.Internal.MeLoaded): State? {
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

    private fun isSecondErrorChange(): Boolean {
        val temp = isSecondError
        isSecondError = isSecondError.not()
        return temp
    }
}
