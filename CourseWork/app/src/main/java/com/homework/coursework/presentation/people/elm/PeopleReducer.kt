package com.homework.coursework.presentation.people.elm

import com.homework.coursework.presentation.interfaces.TwoSourceHandleReducer
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject


class PeopleReducer @Inject constructor() : DslReducer<Event, State, Effect, Command>(),
    TwoSourceHandleReducer<Event.Internal.UserLoaded, State> {
    override fun Result.reduce(event: Event) = when (event) {
        is Event.Internal.ErrorLoading -> {
            state { copy(error = event.error, isError = false, isUpdate = true, isLoading = true) }
        }
        is Event.Internal.UserLoaded -> {
            if (isWithError(event)) {
                handleResult(event)?.let { state { it } }
                effects { +Effect.UserLoadedError(event.itemList.first().errorHandle.error) }
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
        Event.Ui.LoadUsers -> {
            state { copy(isLoading = true, isError = false, isUpdate = false) }
            commands { +Command.LoadUsers }
        }
        Event.Ui.OnStop -> {
            isSecondError = false
        }
        is Event.Ui.SearchUsers -> {
            state { copy(isLoading = true, isUpdate = false, isError = false) }
            commands { +Command.SearchUsers(event.query) }
        }
    }

    override var isSecondError: Boolean = false

    override fun handleResult(event: Event.Internal.UserLoaded): State? {
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

    override fun isWithError(event: Event.Internal.UserLoaded): Boolean {
        return event.itemList.isNotEmpty() && event.itemList.first().errorHandle.isError
    }
}
