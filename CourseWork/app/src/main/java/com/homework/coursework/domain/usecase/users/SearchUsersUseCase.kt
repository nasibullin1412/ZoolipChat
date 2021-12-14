package com.homework.coursework.domain.usecase.users

import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.domain.repository.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

interface SearchUsersUseCase : (String) -> Observable<List<UserData>> {
    override fun invoke(query: String): Observable<List<UserData>>
}

class SearchUsersUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : SearchUsersUseCase {
    override fun invoke(query: String): Observable<List<UserData>> {
        return userRepository.getAllUsers()
            .map { userList ->
                userList.filter { it.name.contains(query) || it.userMail.contains(query) }
            }
    }
}
