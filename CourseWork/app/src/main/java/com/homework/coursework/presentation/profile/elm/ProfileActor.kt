package com.homework.coursework.presentation.profile.elm

import com.homework.coursework.domain.usecase.GetMeUseCase
import com.homework.coursework.presentation.adapter.mapper.UserItemMapper
import io.reactivex.Observable
import vivid.money.elmslie.core.ActorCompat

class ProfileActor(
    private val getMe: GetMeUseCase,
    private val userItemMapper: UserItemMapper
) : ActorCompat<Command, Event> {

    override fun execute(command: Command): Observable<Event> = when (command) {
        Command.LoadMe -> getMe()
            .map(userItemMapper)
            .mapEvents(
                { item -> Event.Internal.MeLoaded(item = item) },
                { error -> Event.Internal.ErrorLoading(error = error) }
            )
    }
}
