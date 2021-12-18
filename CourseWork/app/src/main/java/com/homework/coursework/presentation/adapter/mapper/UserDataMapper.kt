package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.domain.entity.UserStatus
import com.homework.coursework.presentation.adapter.data.UserItem
import dagger.Reusable
import javax.inject.Inject

@Reusable
class UserDataMapper @Inject constructor() : (UserItem) -> (UserData) {
    override fun invoke(userItem: UserItem) = userItem.run {
        UserData(
            id = userId,
            name = name,
            avatarUrl = avatarUrl,
            userMail = userMail,
            userStatus = UserStatus(),
            isAdmin = isAdmin
        )
    }
}