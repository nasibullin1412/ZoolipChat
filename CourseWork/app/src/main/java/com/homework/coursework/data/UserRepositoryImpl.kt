package com.homework.coursework.data

import android.util.Log
import com.homework.coursework.data.dto.StatusDto
import com.homework.coursework.data.dto.UserDto
import com.homework.coursework.data.dto.UserWithStatus
import com.homework.coursework.data.frameworks.database.dao.CurrentProfileDao
import com.homework.coursework.data.frameworks.database.dao.UserDao
import com.homework.coursework.data.frameworks.database.entities.CurrentProfileEntity
import com.homework.coursework.data.frameworks.database.entities.UserEntity
import com.homework.coursework.data.frameworks.database.mappersimpl.*
import com.homework.coursework.data.frameworks.network.ApiService
import com.homework.coursework.data.frameworks.network.mappersimpl.UserDtoMapper
import com.homework.coursework.data.frameworks.network.mappersimpl.UserListDtoMapper
import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.domain.repository.UserRepository
import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class UserRepositoryImpl @Inject constructor(
    private val _apiService: Lazy<ApiService>,
    private val userDao: UserDao,
    private val currentProfileDao: CurrentProfileDao
) : UserRepository {

    @Inject
    internal lateinit var userDtoMapper: UserDtoMapper

    @Inject
    internal lateinit var userEntityMapper: UserEntityMapper

    @Inject
    internal lateinit var userDataMapper: UserDataMapper

    @Inject
    internal lateinit var profileEntityMapper: ProfileEntityMapper

    @Inject
    internal lateinit var profileDataMapper: ProfileDataMapper

    @Inject
    internal lateinit var userDtoListMapper: UserListDtoMapper

    @Inject
    internal lateinit var userDataListMapper: UserDataListMapper

    @Inject
    internal lateinit var userEntityListMapper: UserEntityListMapper

    @Inject
    internal lateinit var compositeDisposable: CompositeDisposable

    private val apiService: ApiService get() = _apiService.get()

    private fun getStatus(userDto: UserDto): Observable<StatusDto> {
        return apiService.getStatus(userDto.id)
            .map { it.data }
    }

    override fun getMe(): Observable<UserData> {
        return Observable.concatArrayEagerDelayError(
            getLocalMe(),
            getRemoteMe()
        )
    }

    private fun getRemoteMe(): Observable<UserData> {
        return apiService.getMe()
            .flatMap { userDto -> zipUserAndStatus(userDto) }
            .map(userDtoMapper)
            .doOnNext { storeMeInDb(profileDataMapper(it)) }
            .onErrorReturn { error: Throwable ->
                UserData.getErrorObject(error)
            }
    }

    private fun getLocalMe(): Observable<UserData> {
        return currentProfileDao.getCurrentProfile()
            .map(profileEntityMapper)
            .toObservable()
            .onErrorReturn { error: Throwable ->
                UserData.getErrorObject(error)
            }
    }

    private fun storeMeInDb(users: CurrentProfileEntity) {
        Observable.fromCallable { currentProfileDao.insert(users) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribeBy(
                onNext = { Log.d("FromRoom", it.toString()) },
                onError = { Log.e("FromRoom", it.message.toString()) }
            )
            .addTo(compositeDisposable)
    }

    private fun getLocalUser(userId: Long): Observable<UserData> {
        return userDao.getUser(userId)
            .map(userEntityMapper)
            .toObservable()
            .onErrorReturn { error: Throwable ->
                UserData.getErrorObject(error)
            }
    }

    private fun storeUserInDb(users: UserEntity) {
        Observable.fromCallable { userDao.insert(users) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribeBy(
                onNext = { Log.d("FromRoom", it.toString()) },
                onError = { Log.e("FromRoom", it.message.toString()) }
            )
            .addTo(compositeDisposable)
    }


    private fun storeUsersInDb(users: List<UserEntity>) {
        Observable.fromCallable { userDao.insertAll(users) }
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
            .doOnNext { storeUserInDb(userDataMapper(it)) }
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

    override fun getUserId(): Observable<Int> {
        return currentProfileDao.getCurrentUserId()
            .toObservable()
    }

    override fun delete(): Completable {
        return currentProfileDao.delete()
    }

    override fun getAllUsers(): Observable<List<UserData>> {
        return Observable.concatArrayEagerDelayError(
            getLocalAllUsers(),
            getRemoteAllUsers()
        )
    }

    private fun getLocalAllUsers(): Observable<List<UserData>> {
        return userDao.getAllUsers()
            .map(userEntityListMapper)
            .toObservable()
            .onErrorReturn { error: Throwable ->
                listOf(UserData.getErrorObject(error))
            }
    }

    private fun getRemoteAllUsers(): Observable<List<UserData>> {
        return apiService.getAllUsers()
            .map(userDtoListMapper)
            .doOnNext { storeUsersInDb(userDataListMapper(it)) }
            .onErrorReturn { error: Throwable ->
                listOf(UserData.getErrorObject(error))
            }
    }
}
