package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.presentation.adapter.data.ErrorHandle
import com.homework.coursework.presentation.adapter.data.UserItem
import dagger.Reusable
import javax.inject.Inject

@Reusable
class UserItemMapper @Inject constructor() : (UserData, Int) -> (UserItem) {
    override fun invoke(userData: UserData, idx: Int) = userData.run {
        UserItem(
            id = idx,
            userId = id,
            name = name,
            avatarUrl = avatarUrl,
            userMail = userMail,
            userStatus = userStatus,
            lastStatusDate = userStatus.timestamp,
            isAdmin = isAdmin,
            errorHandle = ErrorHandle(isError = errorHandle.isError, error = errorHandle.error)
        )
    }
}
