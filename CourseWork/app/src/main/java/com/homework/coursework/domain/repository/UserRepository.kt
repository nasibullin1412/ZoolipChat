package com.homework.coursework.domain.repository

import com.homework.coursework.domain.entity.UserData
import io.reactivex.Completable
import io.reactivex.Observable

interface UserRepository {
    fun getMe(): Observable<UserData>
    fun getUser(id: Int): Observable<UserData>
    fun getUserId(): Observable<Int>
    fun delete(): Completable
}
