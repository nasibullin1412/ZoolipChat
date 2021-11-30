package com.homework.coursework.domain.repository

import com.homework.coursework.domain.entity.UserData
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {
    fun getMe(): Observable<UserData>
    fun getUser(id: Int): Observable<UserData>
}
