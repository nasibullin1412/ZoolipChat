package com.homework.coursework.domain.usecase

import com.homework.coursework.domain.repository.AuthRepository
import com.homework.coursework.domain.repository.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

interface LogoutUserUseCase : () -> (Completable) {
    override fun invoke(): Completable
}

class LogoutUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : LogoutUserUseCase {
    override fun invoke(): Completable {
        return authRepository.delete().concatWith(userRepository.delete())
    }
}
