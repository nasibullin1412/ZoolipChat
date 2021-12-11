package com.homework.coursework.presentation.ui.createstream.elm

import com.homework.coursework.di.CreateNewStreamFragmentScope
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject

@CreateNewStreamFragmentScope
class CreateStreamReducer @Inject constructor() : DslReducer<Event, State, Effect, Command>() {
    override fun Result.reduce(event: Event) = when (event) {
        is Event.Internal.ErrorSubscribe -> {
            effects { +Effect.ErrorSubscribe(event.error) }
        }
        is Event.Internal.SuccessSubscribe -> {
            effects { +Effect.SuccessSubscribe(event.data) }
        }
        is Event.Ui.SubscribeToStream -> {
            commands { +Command.SubscribeToStream(subscribeData = event.subscribeData) }
        }
    }
}
