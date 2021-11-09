package com.homework.coursework.domain.usecase

import com.homework.coursework.data.UserRepositoryImpl
import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.domain.repository.UserRepository
import io.reactivex.Observable

interface GetMeUseCase : () -> (Observable<UserData>) {
    override fun invoke(): Observable<UserData>
}

class GetMeUseCaseImpl : GetMeUseCase {

    private val userRepository: UserRepository = UserRepositoryImpl()

    override fun invoke(): Observable<UserData> {
        return userRepository.getUser()
    }
}
