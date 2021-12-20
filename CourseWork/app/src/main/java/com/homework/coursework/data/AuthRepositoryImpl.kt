package com.homework.coursework.data

import android.util.Log
import com.homework.coursework.data.frameworks.database.dao.AuthDao
import com.homework.coursework.data.frameworks.database.entities.AuthEntity
import com.homework.coursework.data.frameworks.database.mappersimpl.AuthDataMapper
import com.homework.coursework.data.frameworks.database.mappersimpl.AuthEntityMapper
import com.homework.coursework.data.frameworks.network.ApiService
import com.homework.coursework.data.frameworks.network.mappersimpl.AuthDtoMapper
import com.homework.coursework.domain.entity.AuthData
import com.homework.coursework.domain.repository.AuthRepository
import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val _apiService: Lazy<ApiService>,
    private val authDao: AuthDao
) : AuthRepository {

    @Inject
    internal lateinit var authDtoMapper: AuthDtoMapper

    @Inject
    internal lateinit var authEntityMapper: AuthEntityMapper

    @Inject
    internal lateinit var authDataMapper: AuthDataMapper

    @Inject
    internal lateinit var compositeDisposable: CompositeDisposable

    private val apiService get() = _apiService.get()

    override fun authUser(login: String, password: String): Observable<AuthData> {
        return apiService.authUser(login, password)
            .map(authDtoMapper)
            .doOnNext { storeUsersInDb(authDataMapper(it)) }
    }

    override fun checkIsAuth(): Single<AuthData> = run{
        Single.fromCallable {
            authDao.getApiKey()
        }.map(authEntityMapper)
    }

    override fun delete(): Completable {
        return authDao.delete()
    }

    private fun storeUsersInDb(authEntity: AuthEntity) {
        authDao.insertApiKey(authEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribeBy(
                onComplete = { Log.d("FromRoom", "success") },
                onError = { Log.e("FromRoom", it.message.toString()) }
            )
            .addTo(compositeDisposable)
    }
}
