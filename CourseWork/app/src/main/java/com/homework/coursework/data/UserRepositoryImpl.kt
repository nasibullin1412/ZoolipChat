package com.homework.coursework.data

import com.homework.coursework.data.mappers.UserDtoMapper
import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.domain.repository.UserRepository
import com.homework.coursework.presentation.App
import io.reactivex.Observable
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class UserRepositoryImpl : UserRepository {

    private val userDtoMapper: UserDtoMapper = UserDtoMapper()

    override fun getUser(): Observable<UserData> {
        return App.instance.apiService.getMe()
            .map(userDtoMapper)
    }

    override fun getUser(id: Int): Observable<UserData> {
        throw NotImplementedError()
    }
}
