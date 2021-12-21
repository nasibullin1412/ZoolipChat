package com.homework.coursework.presentation.ui.stream.elm

import com.homework.coursework.di.StreamFragmentScope
import com.homework.coursework.presentation.interfaces.TwoSourceHandleReducer
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject

@StreamFragmentScope
class StreamReducer @Inject constructor() : DslReducer<Event, State, Effect, Command>(),
    TwoSourceHandleReducer<Event.Internal.StreamLoaded, State> {

    override var isSecondError = false

    override fun Result.reduce(event: Event) = when (event) {
        is Event.Internal.ErrorLoading -> errorLoading(event.error)
        is Event.Internal.StreamLoaded -> streamLoaded(event)
        Event.Ui.LoadAllStreams -> {
            state { copy(isLoading = true, isUpdate = false, isError = false) }
            commands { +Command.LoadAllStreams }
        }
        Event.Ui.LoadSubscribedStreams -> {
            state { copy(isLoading = true, isUpdate = false, isError = false) }
            commands { +Command.LoadSubscribedStreams }
        }
        is Event.Ui.SearchStreams -> {
            state { copy(isLoading = true, isUpdate = false, isError = false) }
            commands { +Command.SearchStreams(event.query) }
        }
        is Event.Ui.SearchSubscribedStreams -> {
            state { copy(isLoading = true, isUpdate = false, isError = false) }
            commands { +Command.SearchSubscribedStreams(event.query) }
        }
        Event.Ui.OnStop -> {
            isSecondError = false
        }
    }

    override fun handleResult(event: Event.Internal.StreamLoaded): State? {
        return if (isSecondError) {
            State(
                isError = isSecondErrorChange(),
                error = event.itemList.first().errorHandle.error
            )
        } else {
            isSecondErrorChange()
            null
        }
    }

    override fun isWithError(event: Event.Internal.StreamLoaded): Boolean {
        return event.itemList.isNotEmpty() && event.itemList.first().errorHandle.isError
    }

    private fun Result.errorLoading(error: Throwable) {
        state {
            copy(
                error = error,
                isError = true,
                isLoading = false,
                isUpdate = false
            )
        }
        effects { +Effect.StreamLoadError(error) }
    }

    private fun Result.streamLoaded(event: Event.Internal.StreamLoaded) {
        if (isWithError(event)) {
            handleResult(event)?.let { state { it } }
            effects { +Effect.StreamLoadError(event.itemList.first().errorHandle.error) }
        } else {
            isSecondError = false
            state {
                copy(
                    itemList = event.itemList,
                    isUpdate = true,
                    isLoading = false,
                    isError = false
                )
            }
        }
    }
}
