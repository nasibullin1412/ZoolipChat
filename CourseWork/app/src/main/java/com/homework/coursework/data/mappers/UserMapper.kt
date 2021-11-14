package com.homework.coursework.data.mappers

import com.homework.coursework.domain.entity.UserData

interface UserMapper <T>: (T) -> (UserData){
    override fun invoke(user: T): UserData
}