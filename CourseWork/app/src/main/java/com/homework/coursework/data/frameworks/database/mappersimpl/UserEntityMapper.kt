package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.UserEntity
import com.homework.coursework.data.mappers.UserMapper
import com.homework.coursework.domain.entity.UserData

class UserEntityMapper : UserMapper<UserEntity> {

    private val statusEntityMapper: StatusEntityMapper = StatusEntityMapper()

    override fun invoke(user: UserEntity): UserData {
        return with(user) {
            UserData(
                id = userId,
                name = fullName,
                avatarUrl = avatarUrl,
                userMail = email,
                userStatus = statusEntityMapper(userStatus, userTimestamp)
            )
        }
    }
}
