package com.homework.coursework.presentation.profile.elm

import com.homework.coursework.domain.usecase.GetMeUseCase
import com.homework.coursework.domain.usecase.GetUserProfileUseCase
import com.homework.coursework.domain.usecase.LogoutUserUseCase
import com.homework.coursework.presentation.adapter.mapper.UserItemMapper
import io.reactivex.Observable
import vivid.money.elmslie.core.ActorCompat

class ProfileActor(
    private val getMe: GetMeUseCase,
    private val getUser: GetUserProfileUseCase,
    private val logoutUser: LogoutUserUseCase,
    private val userItemMapper: UserItemMapper
) : ActorCompat<Command, Event> {

    override fun execute(command: Command): Observable<Event> = when (command) {
        Command.LoadMe -> {
            getMe()
                .map(userItemMapper)
                .mapEvents(
                    { item -> Event.Internal.UserLoaded(item = item) },
                    { error -> Event.Internal.ErrorLoading(error = error) }
                )
        }
        is Command.LoadUser -> {
            getUser(command.id)
                .map(userItemMapper)
                .mapEvents(
                    { item -> Event.Internal.UserLoaded(item = item) },
                    { error -> Event.Internal.ErrorLoading(error = error) }
                )
        }
        Command.LogoutUser -> {
            logoutUser()
                .mapEvents(Event.Internal.SuccessLogout, Event.Internal.ErrorLogout)
        }
    }
}
