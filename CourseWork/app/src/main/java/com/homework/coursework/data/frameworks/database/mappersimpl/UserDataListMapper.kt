package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.UserEntity
import com.homework.coursework.domain.entity.UserData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class UserDataListMapper @Inject constructor() : (List<UserData>) -> (List<UserEntity>) {

    @Inject
    internal lateinit var userDataMapper: UserDataMapper

    override fun invoke(userList: List<UserData>): List<UserEntity> {
        val userEntityList: ArrayList<UserEntity> = arrayListOf()
        for (user in userList) {
            userEntityList.add(userDataMapper(user))
        }
        return userEntityList
    }
}
