package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.CurrentProfileEntity
import com.homework.coursework.domain.entity.UserData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class ProfileEntityMapper @Inject constructor() : (CurrentProfileEntity) -> (UserData) {

    @Inject
    internal lateinit var statusEntityMapper: StatusEntityMapper

    override fun invoke(user: CurrentProfileEntity): UserData {
        return with(user) {
            UserData(
                id = userId,
                name = fullName,
                avatarUrl = avatarUrl,
                userMail = email,
                isAdmin = user.isAdmin,
                userStatus = statusEntityMapper(userStatus, userTimestamp)
            )
        }
    }
}
