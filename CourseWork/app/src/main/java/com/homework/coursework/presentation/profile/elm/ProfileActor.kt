package com.homework.coursework.presentation.profile.elm

import com.homework.coursework.domain.usecase.GetMeUseCase
import com.homework.coursework.domain.usecase.GetUserProfileUseCase
import com.homework.coursework.presentation.adapter.mapper.UserItemMapper
import io.reactivex.Observable
import vivid.money.elmslie.core.ActorCompat

class ProfileActor(
    private val getMe: GetMeUseCase,
    private val getUser: GetUserProfileUseCase,
    private val userItemMapper: UserItemMapper
) : ActorCompat<Command, Event> {

    override fun execute(command: Command): Observable<Event> {
        val useCase = when (command) {
            Command.LoadMe -> getMe()
            is Command.LoadUser -> getUser(command.id)
        }
        return useCase.map(userItemMapper)
            .mapEvents(
                { item -> Event.Internal.UserLoaded(item = item) },
                { error -> Event.Internal.ErrorLoading(error = error) }
            )
    }
}
