package com.homework.coursework.data.mappers

import com.homework.coursework.data.dto.UserDto
import com.homework.coursework.domain.entity.UserData

class UserDtoMapper: (UserDto) -> (UserData) {
    override fun invoke(user: UserDto): UserData {
        return with(user){
            UserData(
                id = id,
                name = fullName,
                avatarUrl = avatarUrl,
                userMail = email
            )
        }
    }

}