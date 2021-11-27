package com.homework.coursework.domain.usecase

import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.domain.repository.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

interface GetUserProfileUseCase : (Int) -> (Observable<UserData>) {
    override fun invoke(id: Int): Observable<UserData>
}

class GetUserProfileUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetUserProfileUseCase {
    override fun invoke(id: Int): Observable<UserData> {
        return userRepository.getUser(id)
    }
}
