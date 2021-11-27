package com.homework.coursework.domain.usecase

import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.domain.repository.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

interface GetMeUseCase : () -> (Observable<UserData>) {
    override fun invoke(): Observable<UserData>
}

class GetMeUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetMeUseCase {

    override fun invoke(): Observable<UserData> {
        return userRepository.getUser()
    }
}
