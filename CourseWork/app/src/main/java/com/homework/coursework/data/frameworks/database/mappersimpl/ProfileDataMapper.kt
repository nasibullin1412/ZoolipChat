package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.CurrentProfileEntity
import com.homework.coursework.domain.entity.UserData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class ProfileDataMapper @Inject constructor(): (UserData) -> (CurrentProfileEntity) {

    override fun invoke(userData: UserData): CurrentProfileEntity {
        return with(userData) {
            CurrentProfileEntity(
                id = 0,
                userId = id,
                email = userMail,
                fullName = name,
                avatarUrl = avatarUrl,
                isAdmin = isAdmin,
                userStatus = userStatus.status.value,
                userTimestamp = userStatus.timestamp
            )
        }
    }
}