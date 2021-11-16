package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.UserEntity
import com.homework.coursework.domain.entity.UserData

class UserDataListMapper : (List<UserData>) -> (List<UserEntity>) {

    private val userDataMapper: UserDataMapper = UserDataMapper()

    override fun invoke(userList: List<UserData>): List<UserEntity> {
        val userEntityList: ArrayList<UserEntity> = arrayListOf()
        for (user in userList) {
            userEntityList.add(userDataMapper(user))
        }
        return userEntityList
    }
}
