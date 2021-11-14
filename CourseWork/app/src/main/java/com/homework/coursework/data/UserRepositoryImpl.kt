package com.homework.coursework.data

import android.util.Log
import com.homework.coursework.data.dto.StatusDto
import com.homework.coursework.data.dto.UserDto
import com.homework.coursework.data.dto.UserWithStatus
import com.homework.coursework.data.frameworks.database.AppDatabase
import com.homework.coursework.data.frameworks.database.entities.UserEntity
import com.homework.coursework.data.frameworks.database.mappersimpl.UserDataMapper
import com.homework.coursework.data.frameworks.database.mappersimpl.UserEntityMapper
import com.homework.coursework.data.frameworks.network.mappersimpl.UserDtoMapper
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.USER_ID
import com.homework.coursework.data.mappers.UserMapper
import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.domain.repository.UserRepository
import com.homework.coursework.presentation.App
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class UserRepositoryImpl : UserRepository {

    private val userDtoMapper: UserMapper<UserWithStatus> = UserDtoMapper()
    private val userEntityMapper: UserMapper<UserEntity> = UserEntityMapper()
    private val userDataMapper: UserDataMapper = UserDataMapper()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private fun getStatus(userDto: UserDto): Observable<StatusDto> {
        return App.instance.apiService.getStatus(userDto.id)
            .map { it.data }
    }

    override fun getUser(): Observable<UserData> {
        return Observable.concatArrayEagerDelayError(
            getLocalUser(),
            getRemoteUser()
        )
            .doOnNext { Log.d("ffffff", it.toString()) }
    }

    private fun getRemoteUser(): Observable<UserData> {
        return App.instance.apiService.getMe()
            .subscribeOn(Schedulers.io())
            .flatMap { userDto -> zipUserAndStatus(userDto) }
            .map(userDtoMapper)
            .doOnNext { storeUsersInDb(userDataMapper(it)) }
            .onErrorReturn { error: Throwable ->
                UserData.getErrorObject(error)
            }
    }

    private fun getLocalUser(userId: Long = USER_ID.toLong()): Observable<UserData> {
        return AppDatabase.instance.userDao().getUser(userId)
            .doAfterSuccess { Log.d("CheckGetUserLocal", it.toString()) }
            .map(userEntityMapper)
            .toObservable()
            .onErrorReturn { error: Throwable ->
                UserData.getErrorObject(error)
            }

    }

    private fun storeUsersInDb(users: UserEntity) {
        Observable.fromCallable { AppDatabase.instance.userDao().insert(users) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribeBy(
                onNext = { Log.d("FromRoom", it.toString()) },
                onError = { Log.e("FromRoom", it.message.toString()) }
            )
            .addTo(compositeDisposable)

    }

    private fun zipUserAndStatus(userDto: UserDto): Observable<UserWithStatus> {
        return Observable.zip(
            Observable.just(userDto),
            getStatus(userDto)
        )
        { _: UserDto, status: StatusDto ->
            Pair(userDto, status)
        }
    }

    override fun getUser(id: Int): Observable<UserData> {
        throw NotImplementedError()
    }
}
