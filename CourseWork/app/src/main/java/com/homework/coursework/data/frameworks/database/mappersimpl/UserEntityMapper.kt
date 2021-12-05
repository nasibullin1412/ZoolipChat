package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.UserEntity
import com.homework.coursework.domain.entity.UserData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class UserEntityMapper @Inject constructor() : (UserEntity) -> (UserData) {

    @Inject
    internal lateinit var statusEntityMapper: StatusEntityMapper

    override fun invoke(user: UserEntity): UserData {
        return with(user) {
            UserData(
                id = userId,
                name = fullName,
                avatarUrl = avatarUrl,
                userMail = email,
                isAdmin = isAdmin,
                userStatus = statusEntityMapper(userStatus, userTimestamp)
            )
        }
    }
}
