package com.homework.coursework.presentation.ui.createstream.elm

import vivid.money.elmslie.core.ElmStoreCompat

class CreateStreamStoreFactory(
    private val createStreamActor: CreateStreamActor,
    private val createStreamReducer: CreateStreamReducer
) {
    private val store by lazy {
        ElmStoreCompat(
            initialState = State(),
            reducer = createStreamReducer,
            actor = createStreamActor
        )
    }

    fun provide() = store
}
