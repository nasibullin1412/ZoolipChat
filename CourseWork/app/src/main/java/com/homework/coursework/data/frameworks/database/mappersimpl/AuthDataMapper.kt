package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.AuthEntity
import com.homework.coursework.domain.entity.AuthData

class AuthDataMapper : (AuthData) -> (AuthEntity) {
    override fun invoke(authData: AuthData): AuthEntity {
        return with(authData) { AuthEntity(id = 0, apiKey = apiKey, email = email) }
    }
}
