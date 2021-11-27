package com.homework.coursework.data

import android.util.Log
import com.homework.coursework.data.dto.StatusDto
import com.homework.coursework.data.dto.UserDto
import com.homework.coursework.data.dto.UserWithStatus
import com.homework.coursework.data.frameworks.database.dao.UserDao
import com.homework.coursework.data.frameworks.database.entities.UserEntity
import com.homework.coursework.data.frameworks.database.mappersimpl.UserDataMapper
import com.homework.coursework.data.frameworks.database.mappersimpl.UserEntityMapper
import com.homework.coursework.data.frameworks.network.ApiService
import com.homework.coursework.data.frameworks.network.mappersimpl.UserDtoMapper
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.USER_ID
import com.homework.coursework.di.UserComposite
import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.domain.repository.UserRepository
import dagger.Lazy
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@UserComposite
@ExperimentalSerializationApi
class UserRepositoryImpl @Inject constructor(
    private val _apiService: Lazy<ApiService>,
    private val userDao: UserDao
) : UserRepository {

    @Inject
    internal lateinit var userDtoMapper: UserDtoMapper

    @Inject
    internal lateinit var userEntityMapper: UserEntityMapper

    @Inject
    internal lateinit var userDataMapper: UserDataMapper

    @UserComposite
    internal lateinit var compositeDisposable: CompositeDisposable

    private val apiService: ApiService get() = _apiService.get()

    private fun getStatus(userDto: UserDto): Observable<StatusDto> {
        return apiService.getStatus(userDto.id)
            .map { it.data }
    }

    override fun getUser(): Observable<UserData> {
        return Observable.concatArrayEagerDelayError(
            getLocalUser(),
            getRemoteMe()
        )
    }

    private fun getRemoteMe(): Observable<UserData> {
        return apiService.getMe()
            .flatMap { userDto -> zipUserAndStatus(userDto) }
            .map(userDtoMapper)
            .doOnNext { storeUsersInDb(userDataMapper(it)) }
            .onErrorReturn { error: Throwable ->
                UserData.getErrorObject(error)
            }
    }

    private fun getLocalUser(userId: Long = USER_ID.toLong()): Observable<UserData> {
        return userDao.getUser(userId)
            .map(userEntityMapper)
            .toObservable()
            .onErrorReturn { error: Throwable ->
                UserData.getErrorObject(error)
            }

    }

    private fun storeUsersInDb(users: UserEntity) {
        Observable.fromCallable { userDao.insert(users) }
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
        ) { _: UserDto, status: StatusDto ->
            Pair(userDto, status)
        }
    }

    private fun getRemoteUser(id: Int): Observable<UserData> {
        return apiService.getUser(id)
            .flatMap { userResponse -> zipUserAndStatus(userResponse.data!!) }
            .map(userDtoMapper)
            .doOnNext { storeUsersInDb(userDataMapper(it)) }
            .onErrorReturn { error: Throwable ->
                UserData.getErrorObject(error)
            }
    }

    override fun getUser(id: Int): Observable<UserData> {
        return Observable.concatArrayEagerDelayError(
            getLocalUser(userId = id.toLong()),
            getRemoteUser(id)
        )
    }
}
