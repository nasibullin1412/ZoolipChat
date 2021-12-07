package com.homework.coursework.data.frameworks.network.mappersimpl

import com.homework.coursework.data.dto.AuthDto
import com.homework.coursework.domain.entity.AuthData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class AuthDtoMapper @Inject constructor() : (AuthDto) -> (AuthData) {
    override fun invoke(authDto: AuthDto): AuthData {
        return with(authDto) {
            AuthData(apiKey = apiKey, email = email)
        }
    }
}
