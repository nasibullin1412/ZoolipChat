package com.homework.coursework.presentation.authorization.elm

import vivid.money.elmslie.core.ElmStoreCompat

class AuthStoreFactory(
    private val authActor: AuthActor,
    private val authReducer: AuthReducer
) {

    private val store by lazy {
        ElmStoreCompat(
            initialState = State(),
            reducer = authReducer,
            actor = authActor
        )
    }

    fun provide() = store
}
