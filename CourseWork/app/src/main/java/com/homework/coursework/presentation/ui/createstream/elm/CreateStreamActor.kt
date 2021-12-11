package com.homework.coursework.presentation.ui.createstream.elm

import com.homework.coursework.domain.usecase.SubscribeToStreamUseCase
import io.reactivex.Observable
import vivid.money.elmslie.core.ActorCompat

class CreateStreamActor(
    private val subscribeToStreamUseCase: SubscribeToStreamUseCase
) : ActorCompat<Command, Event> {
    override fun execute(command: Command): Observable<Event> = when (command) {
        is Command.SubscribeToStream -> {
            subscribeToStreamUseCase(command.subscribeData)
                .mapEvents(
                    { item -> Event.Internal.SuccessSubscribe(item) },
                    { error -> Event.Internal.ErrorSubscribe(error) }
                )
        }
    }
}
