package com.homework.coursework.domain.usecase

import com.homework.coursework.domain.repository.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

interface GetCurrentUserIdUseCase : () -> (Observable<Int>)

class GetCurrentUserIdUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetCurrentUserIdUseCase {
    override fun invoke(): Observable<Int> {
        return userRepository.getUserId()
    }
}
