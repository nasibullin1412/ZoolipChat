package com.homework.coursework.presentation.authorization.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject

class AuthReducer @Inject constructor() : DslReducer<Event, State, Effect, Command>() {

    override fun Result.reduce(event: Event) = when (event) {
        is Event.Internal.SuccessAuth -> {
            effects { +Effect.SuccessAuth(event.apiToken) }
        }
        is Event.Internal.ErrorAuth -> {
            effects { +Effect.ErrorAuth(event.throwable) }
        }
        Event.Ui.CheckDatabase -> {
            commands { +Command.CheckIsAuth }
        }
        is Event.Ui.PressButton -> {
            commands { +Command.AuthUser(login = event.login, password = event.password) }
        }
    }
}
