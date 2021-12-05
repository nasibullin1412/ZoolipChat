package com.homework.coursework.data.frameworks.network.mappersimpl

import com.homework.coursework.data.dto.StatusDto
import com.homework.coursework.data.dto.UserResponseList
import com.homework.coursework.data.dto.UserWithStatus
import com.homework.coursework.domain.entity.UserData
import dagger.Reusable
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@Reusable
class UserListDtoMapper @Inject constructor() : (UserResponseList) -> (List<UserData>) {

    @Inject
    internal lateinit var userDtoMapper: UserDtoMapper

    override fun invoke(userDtoList: UserResponseList): List<UserData> {
        return userDtoList.data?.map {
            userDtoMapper(
                UserWithStatus(
                    it,
                    StatusDto.getEmptyStatus()
                )
            )
        } ?: emptyList()
    }
}