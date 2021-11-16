package com.homework.coursework.data.frameworks.network.mappersimpl

import com.homework.coursework.data.dto.UserWithStatus
import com.homework.coursework.data.mappers.UserMapper
import com.homework.coursework.domain.entity.UserData
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class UserDtoMapper : UserMapper<UserWithStatus> {
    private val statusDtoMapper: StatusDtoMapper = StatusDtoMapper()
    override fun invoke(user: UserWithStatus): UserData {
        return with(user) {
            UserData(
                id = first.id,
                name = first.fullName,
                avatarUrl = first.avatarUrl,
                userMail = first.email,
                userStatus = statusDtoMapper(second)
            )
        }
    }
}
