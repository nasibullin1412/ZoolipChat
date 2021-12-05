package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.UserEntity
import com.homework.coursework.domain.entity.UserData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class UserEntityListMapper @Inject constructor() : (List<UserEntity>) -> (List<UserData>) {

    @Inject
    internal lateinit var userEntityMapper: UserEntityMapper

    override fun invoke(userEntityList: List<UserEntity>): List<UserData> {
        return userEntityList.map(userEntityMapper)
    }
}
