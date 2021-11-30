package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.AuthEntity
import com.homework.coursework.domain.entity.AuthData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class AuthEntityMapper @Inject constructor() : (AuthEntity) -> (AuthData) {
    override fun invoke(authEntity: AuthEntity): AuthData {
        return with(authEntity) {
            AuthData(apiKey = apiKey, email = email)
        }
    }
}
