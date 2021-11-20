package com.homework.coursework.presentation.stream.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class Reducer : DslReducer<Event, State, Effect, Command>() {

    private var isSecondError = false

    override fun Result.reduce(event: Event) = when (event) {
        is Event.Internal.ErrorLoading -> {
            state { copy(error = event.error, isLoading = false, isSecondError = true) }
        }
        is Event.Internal.StreamLoaded -> {
            if (event.itemList.first().errorHandle.isError) {
                handleResult(event)?.let { state { it } }
            } else {
                state {
                    copy(
                        itemList = event.itemList,
                        isLoading = false,
                        error = null,
                        isSecondError = false
                    )
                }
            }
        }
        Event.Ui.LoadAllStreams -> {
            state { copy(isLoading = true, error = null) }
            commands { +Command.LoadAllStreams }
        }
        Event.Ui.LoadSubscribedStreams -> {
            state { copy(isLoading = true, error = null) }
            commands { +Command.LoadSubscribedStreams }
        }
        is Event.Ui.SearchStreams -> {
            state { copy(isLoading = true, error = null) }
            commands { +Command.SearchStreams(event.query) }
        }
        is Event.Ui.SearchSubscribedStreams -> {
            state { copy(isLoading = true, error = null) }
            commands { +Command.SearchSubscribedStreams(event.query) }
        }
    }

    private fun handleResult(event: Event.Internal.StreamLoaded): State? {
        return if (isSecondError) {
            State(
                itemList = event.itemList,
                isLoading = false,
                isSecondError = isSecondErrorChange(),
                error = event.itemList.first().errorHandle.error
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