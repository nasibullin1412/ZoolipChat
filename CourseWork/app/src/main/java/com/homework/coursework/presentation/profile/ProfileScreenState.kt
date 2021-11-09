package com.homework.coursework.presentation.profile

import com.homework.coursework.presentation.adapter.data.UserItem

sealed class ProfileScreenState {

    class Result(val userItem: UserItem) : ProfileScreenState()

    object Loading : ProfileScreenState()

    class Error(val error: Throwable) : ProfileScreenState()
}
