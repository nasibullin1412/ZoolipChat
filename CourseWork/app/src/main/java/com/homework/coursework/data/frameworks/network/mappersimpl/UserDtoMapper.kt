package com.homework.coursework.data.frameworks.network.mappersimpl

import com.homework.coursework.data.dto.UserWithStatus
import com.homework.coursework.domain.entity.UserData
import dagger.Reusable
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@Reusable
@ExperimentalSerializationApi
class UserDtoMapper @Inject constructor() : (UserWithStatus) -> (UserData) {

    @Inject
    internal lateinit var statusDtoMapper: StatusDtoMapper

    override fun invoke(user: UserWithStatus) = user.run {
        UserData(
            id = first.id,
            name = first.fullName,
            avatarUrl = first.avatarUrl,
            userMail = first.email,
            isAdmin = first.isAdmin,
            userStatus = statusDtoMapper(second)
        )
    }
}
