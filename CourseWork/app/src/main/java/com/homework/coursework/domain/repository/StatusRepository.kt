package com.homework.coursework.domain.repository

import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.domain.entity.UserStatus
import io.reactivex.Observable

interface StatusRepository {
    fun getStatus(userData: UserData): Observable<UserStatus>
}
