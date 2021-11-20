package com.homework.coursework.presentation.stream.elm

import vivid.money.elmslie.core.ElmStoreCompat

class StreamStoreFactory(private val streamActor: StreamActor) {

    private val store by lazy {
        ElmStoreCompat(
            initialState = State(),
            reducer = Reducer(),
            actor = streamActor
        )
    }

    fun provide() = store
}
