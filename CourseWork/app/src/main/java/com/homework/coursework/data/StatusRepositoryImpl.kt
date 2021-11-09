package com.homework.coursework.data

import com.homework.coursework.data.mappers.StatusDtoMapper
import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.domain.entity.UserStatus
import com.homework.coursework.domain.repository.StatusRepository
import com.homework.coursework.presentation.App
import io.reactivex.Observable
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class StatusRepositoryImpl : StatusRepository {

    private val statusDtoMapper: StatusDtoMapper = StatusDtoMapper()

    override fun getStatus(userData: UserData): Observable<UserStatus> {
        return App.instance.apiService.getStatus(userData.id)
            .map(statusDtoMapper)
    }
}