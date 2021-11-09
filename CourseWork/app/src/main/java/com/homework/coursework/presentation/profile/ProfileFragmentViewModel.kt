package com.homework.coursework.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.coursework.domain.usecase.GetMeUseCase
import com.homework.coursework.domain.usecase.GetMeUseCaseImpl
import com.homework.coursework.domain.usecase.GetStatusUseCaseImpl
import com.homework.coursework.presentation.adapter.data.UserItem
import com.homework.coursework.presentation.adapter.mapper.UserItemMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ProfileFragmentViewModel : ViewModel() {
    private var _profileScreenState: MutableLiveData<ProfileScreenState> = MutableLiveData()
    val profileScreenState: LiveData<ProfileScreenState>
        get() = _profileScreenState

    private val getMeUseCase: GetMeUseCase = GetMeUseCaseImpl()
    private val getStatusUseCase: GetStatusUseCaseImpl = GetStatusUseCaseImpl()
    private val userItemMapper: UserItemMapper = UserItemMapper()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getUserData(useCaseType: UseCaseType, id: Int) {
        var userItem: UserItem? = null
        _profileScreenState.value = ProfileScreenState.Loading
        getNeedUseCase(useCaseType, id)
            .subscribeOn(Schedulers.io())
            .doOnError { _profileScreenState.postValue(ProfileScreenState.Error(it)) }
            .doOnNext {
                userItem = userItemMapper(it)
            }
            .switchMap { userData ->
                getStatusUseCase(userData)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    userItem?.userStatus = it
                    _profileScreenState.value =
                        userItem?.let { user -> ProfileScreenState.Result(user) }
                },
                onError = { _profileScreenState.value = ProfileScreenState.Error(it) }
            )
            .addTo(compositeDisposable)
    }

    private fun getNeedUseCase(useCaseType: UseCaseType, id: Int) =
        when (useCaseType) {
            UseCaseType.GET_ME_PROFILE -> getMeUseCase()
            UseCaseType.GET_USER_PROFILE -> throw NotImplementedError()
        }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
