package com.homework.coursework.domain.usecase

import com.homework.coursework.data.StatusRepositoryImpl
import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.domain.entity.UserStatus
import com.homework.coursework.domain.repository.StatusRepository
import io.reactivex.Observable

interface GetStatusUseCase : (UserData) -> (Observable<UserStatus>) {
    override fun invoke(userData: UserData): Observable<UserStatus>
}

class GetStatusUseCaseImpl : GetStatusUseCase {

    private val statusRepository: StatusRepository = StatusRepositoryImpl()

    override fun invoke(userData: UserData): Observable<UserStatus> {
        return statusRepository.getStatus(userData)
    }
}
