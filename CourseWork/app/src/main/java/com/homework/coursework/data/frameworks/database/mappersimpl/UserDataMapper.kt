package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.UserEntity
import com.homework.coursework.domain.entity.UserData

class UserDataMapper : (UserData) -> (UserEntity) {
    override fun invoke(userData: UserData): UserEntity {
        return with(userData) {
            UserEntity(
                id = 0,
                userId = id,
                email = userMail,
                fullName = name,
                avatarUrl = avatarUrl,
                userStatus = userStatus.status.value,
                userTimestamp = userStatus.timestamp
            )
        }
    }
}
