package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.presentation.adapter.data.ErrorHandle
import com.homework.coursework.presentation.adapter.data.UserItem

class UserItemMapper : (UserData) -> (UserItem) {
    override fun invoke(userData: UserData): UserItem {
        return with(userData) {
            UserItem(
                id = id,
                name = name,
                avatarUrl = avatarUrl,
                userMail = userMail,
                userStatus = userStatus,
                lastStatusDate = userStatus.timestamp,
                errorHandle = ErrorHandle(isError = errorHandle.isError, error = errorHandle.error)
            )
        }
    }
}
