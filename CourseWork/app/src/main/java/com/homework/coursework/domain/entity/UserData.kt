package com.homework.coursework.domain.entity

data class UserData(
    val id: Int,
    val name: String,
    val avatarUrl: String,
    val userMail: String,
    val userStatus: UserStatus,
    val isAdmin: Boolean,
    val errorHandle: ErrorHandle = ErrorHandle()
) {
    companion object {
        fun getErrorObject(throwable: Throwable) = UserData(
            id = 0,
            name = "",
            avatarUrl = "",
            userStatus = UserStatus(StatusEnum.UNKNOWN, 0),
            userMail = "",
            isAdmin = false,
            errorHandle = ErrorHandle(
                true, throwable
            )
        )
    }
}
