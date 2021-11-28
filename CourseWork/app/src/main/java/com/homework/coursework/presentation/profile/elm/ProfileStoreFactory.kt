package com.homework.coursework.presentation.profile.elm

import vivid.money.elmslie.core.ElmStoreCompat
import javax.inject.Inject

class ProfileStoreFactory(
    private val profileActor: ProfileActor,
    private val profileReducer: Reducer
) {

    private val store by lazy {
        ElmStoreCompat(
            initialState = State(),
            reducer = profileReducer,
            actor = profileActor
        )
    }

    fun provide() = store
}
