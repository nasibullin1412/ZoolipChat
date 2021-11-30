package com.homework.coursework.domain.usecase

import com.homework.coursework.domain.entity.AuthData
import com.homework.coursework.domain.repository.AuthRepository
import io.reactivex.Observable
import javax.inject.Inject

interface AuthUserUseCase : (String, String) -> (Observable<AuthData>)

class AuthUserUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : AuthUserUseCase {
    override fun invoke(username: String, password: String): Observable<AuthData> {
        return authRepository.authUser(login = username, password = password)
    }
}
