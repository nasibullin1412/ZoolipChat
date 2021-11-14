package com.homework.coursework.presentation.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.coursework.domain.usecase.GetMeUseCase
import com.homework.coursework.domain.usecase.GetMeUseCaseImpl
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
    private val userItemMapper: UserItemMapper = UserItemMapper()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var isSecondError = false

    fun getUserData(useCaseType: UseCaseType, id: Int) {
        _profileScreenState.value = ProfileScreenState.Loading
        getNeedUseCase(useCaseType, id)
            .subscribeOn(Schedulers.io())
            .map(userItemMapper)
            .distinct { it.toString() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    val newScreenState = getNewState(it)
                    if (newScreenState == null) {
                        isSecondError = true
                        return@subscribeBy
                    }
                    _profileScreenState.value = newScreenState
                    isSecondError = false
                },
                onError = {
                    _profileScreenState.value = ProfileScreenState.Error(it)
                }
            )
            .addTo(compositeDisposable)
    }


    private fun getNewState(userItem: UserItem): ProfileScreenState? {
        if (userItem.isError.not()) {
            return ProfileScreenState.Result(userItem)
        }
        if (isSecondError) {
            return ProfileScreenState.Error(userItem.error)
        }
        return null
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
