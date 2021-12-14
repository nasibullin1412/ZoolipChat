package com.homework.coursework.presentation.ui.authorization.elm

import com.homework.coursework.domain.usecase.auth.AuthUserUseCase
import com.homework.coursework.domain.usecase.auth.CheckAuthUseCase
import com.homework.coursework.domain.usecase.users.GetMeUseCase
import io.reactivex.Observable
import vivid.money.elmslie.core.ActorCompat

class AuthActor(
    private val authUserUseCase: AuthUserUseCase,
    private val isCheckAuthUseCase: CheckAuthUseCase,
    private val getMe: GetMeUseCase
) : ActorCompat<Command, Event> {


    override fun execute(command: Command): Observable<Event> = when (command) {
        is Command.AuthUser -> {
            authUserUseCase(command.login, command.password)
                .mapEvents(
                    { item -> Event.Internal.SuccessAuth(item.apiKey) },
                    { error -> Event.Internal.ErrorAuth(error) }
                )
        }
        Command.CheckIsAuth -> {
            isCheckAuthUseCase()
                .mapEvents(
                    { item -> Event.Internal.SuccessAuth(item.apiKey) },
                    { error -> Event.Internal.ErrorAuth(error) }
                )
        }
        Command.GetMe -> {
            getMe()
                .mapEvents(
                    { item -> Event.Internal.SuccessGetMe(item) },
                    { error -> Event.Internal.ErrorAuth(error) }
                )
        }
    }
}
