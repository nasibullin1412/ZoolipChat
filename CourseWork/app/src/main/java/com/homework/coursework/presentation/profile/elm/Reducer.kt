package com.homework.coursework.presentation.profile.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class Reducer : DslReducer<Event, State, Effect, Command>() {
    override fun Result.reduce(event: Event): Any = when (event) {
        is Event.Internal.ErrorLoading -> {
            state { copy(error = event.error, isLoading = false) }
        }
        Event.Ui.LoadMe -> {
            state { copy(isLoading = true, error = null) }
            commands { +Command.LoadMe }
        }
        is Event.Internal.MeLoaded -> {
            if (event.item.errorHandle.isError) {
                state {
                    copy(
                        item = event.item,
                        isLoading = false,
                        error = event.item.errorHandle.error
                    )
                }
            }
            else {
                state { copy(item = event.item, isLoading = false, error = null) }
            }
        }
        is Event.Ui.LoadUser -> {
            throw NotImplementedError()
        }
    }
}
