package com.homework.coursework.presentation.profile.elm

import vivid.money.elmslie.core.ElmStoreCompat

class ProfileStoreFactory(
    private val profileActor: ProfileActor,
    private val profileProfileReducer: ProfileReducer
) {

    private val store by lazy {
        ElmStoreCompat(
            initialState = State(),
            reducer = profileProfileReducer,
            actor = profileActor
        )
    }

    fun provide() = store
}
