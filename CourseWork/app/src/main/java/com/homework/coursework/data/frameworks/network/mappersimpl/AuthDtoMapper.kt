package com.homework.coursework.data.frameworks.network.mappersimpl

import com.homework.coursework.data.dto.AuthDto
import com.homework.coursework.domain.entity.AuthData

class AuthDtoMapper : (AuthDto) -> (AuthData) {
    override fun invoke(authDto: AuthDto): AuthData {
        return with(authDto) {
            AuthData(apiKey = apiKey, email = email)
        }
    }

}