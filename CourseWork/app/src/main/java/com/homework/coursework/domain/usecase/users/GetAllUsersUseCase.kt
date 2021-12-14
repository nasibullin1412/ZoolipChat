package com.homework.coursework.domain.usecase.users

import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.domain.repository.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

interface GetAllUsersUseCase : () -> (Observable<List<UserData>>) {
    override fun invoke(): Observable<List<UserData>>
}

class GetAllUsersUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetAllUsersUseCase {
    override fun invoke(): Observable<List<UserData>> {
        return userRepository.getAllUsers()
    }
}
