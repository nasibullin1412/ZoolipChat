package com.homework.coursework.domain.repository

import com.homework.coursework.domain.entity.AuthData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface AuthRepository {
    fun authUser(login: String, password: String): Observable<AuthData>
    fun checkIsAuth(): Single<AuthData>
    fun delete(): Completable
}
