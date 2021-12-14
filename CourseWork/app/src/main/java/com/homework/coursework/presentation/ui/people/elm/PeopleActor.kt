package com.homework.coursework.presentation.ui.people.elm

import com.homework.coursework.domain.usecase.users.GetAllUsersUseCase
import com.homework.coursework.domain.usecase.users.SearchUsersUseCase
import com.homework.coursework.presentation.adapter.mapper.UserListMapper
import io.reactivex.Observable
import vivid.money.elmslie.core.ActorCompat

class PeopleActor(
    private val getAllUsers: GetAllUsersUseCase,
    private val searchUsers: SearchUsersUseCase,
    private val userItemListMapper: UserListMapper
) : ActorCompat<Command, Event> {
    override fun execute(command: Command): Observable<Event> {
        val useCase = when (command) {
            Command.LoadUsers -> {
                getAllUsers()
            }
            is Command.SearchUsers -> {
                searchUsers(command.query)
            }
        }
        return useCase
            .map(userItemListMapper)
            .mapEvents(
                { list -> Event.Internal.UserLoaded(list) },
                { error -> Event.Internal.ErrorLoading(error) }
            )
    }
}
