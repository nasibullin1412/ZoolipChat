package com.homework.coursework.data

import android.util.Log
import com.homework.coursework.data.frameworks.database.dao.ApiKeyDao
import com.homework.coursework.data.frameworks.database.entities.AuthEntity
import com.homework.coursework.data.frameworks.database.mappersimpl.AuthDataMapper
import com.homework.coursework.data.frameworks.database.mappersimpl.AuthEntityMapper
import com.homework.coursework.data.frameworks.network.ApiService
import com.homework.coursework.data.frameworks.network.mappersimpl.AuthDtoMapper
import com.homework.coursework.domain.entity.AuthData
import com.homework.coursework.domain.repository.AuthRepository
import dagger.Lazy
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class AuthRepositoryImpl @Inject constructor(
    private val _apiService: Lazy<ApiService>,
    private val apiKeyDao: ApiKeyDao
) : AuthRepository {

    private val authDtoMapper: AuthDtoMapper = AuthDtoMapper()

    private val authEntityMapper: AuthEntityMapper = AuthEntityMapper()

    private val authDataMapper: AuthDataMapper = AuthDataMapper()

    @Inject
    internal lateinit var compositeDisposable: CompositeDisposable

    private val apiService get() = _apiService.get()

    override fun authUser(login: String, password: String): Observable<AuthData> {
        return apiService.authUser(login, password)
            .map(authDtoMapper)
            .doOnNext { storeUsersInDb(authDataMapper(it)) }
    }

    override fun checkIsAuth(): Single<AuthData> {
        return Single.fromCallable {
            apiKeyDao.getApiKey()
        }.map(authEntityMapper)
    }

    private fun storeUsersInDb(authEntity: AuthEntity) {
        apiKeyDao.insertApiKey(authEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribeBy(
                onComplete = { Log.d("FromRoom", "success") },
                onError = { Log.e("FromRoom", it.message.toString()) }
            )
            .addTo(compositeDisposable)
    }
}