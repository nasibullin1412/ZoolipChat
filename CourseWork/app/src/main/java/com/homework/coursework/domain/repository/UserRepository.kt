package com.homework.coursework.domain.repository

import com.homework.coursework.domain.entity.UserData
import io.reactivex.Observable

interface UserRepository {
    fun getUser(): Observable<UserData>
    fun getUser(id: Int): Observable<UserData>
}