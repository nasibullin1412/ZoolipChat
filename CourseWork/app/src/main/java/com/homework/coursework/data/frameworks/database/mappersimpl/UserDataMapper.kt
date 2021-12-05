package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.UserEntity
import com.homework.coursework.domain.entity.UserData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class UserDataMapper @Inject constructor() : (UserData) -> (UserEntity) {
    override fun invoke(userData: UserData): UserEntity {
        return with(userData) {
            UserEntity(
                id = 0,
                userId = id,
                email = userMail,
                fullName = name,
                avatarUrl = avatarUrl,
                userStatus = userStatus.status.value,
                isAdmin = isAdmin,
                userTimestamp = userStatus.timestamp
            )
        }
    }
}
