package com.homework.coursework.domain.usecase.auth

import com.homework.coursework.domain.entity.AuthData
import com.homework.coursework.domain.repository.AuthRepository
import io.reactivex.Single
import javax.inject.Inject

interface CheckAuthUseCase : () -> (Single<AuthData>)

class CheckAuthUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : CheckAuthUseCase {
    override fun invoke(): Single<AuthData> {
        return authRepository.checkIsAuth()
    }
}
