package com.homework.coursework.domain.repository

import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.domain.entity.UserStatus
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {
    fun getUser(): Observable<UserData>
    fun getUser(id: Int): Observable<UserData>
}
