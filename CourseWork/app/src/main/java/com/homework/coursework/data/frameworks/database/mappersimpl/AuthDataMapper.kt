package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.AuthEntity
import com.homework.coursework.domain.entity.AuthData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class AuthDataMapper @Inject constructor() : (AuthData) -> (AuthEntity) {
    override fun invoke(authData: AuthData) = authData.run {
        AuthEntity(id = 0, apiKey = apiKey, email = email)
    }
}
