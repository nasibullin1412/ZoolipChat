package com.homework.coursework.presentation.ui.stream.elm

import vivid.money.elmslie.core.ElmStoreCompat

class StreamStoreFactory(
    private val streamActor: StreamActor,
    private val streamReducer: StreamReducer
) {

    private val store by lazy {
        ElmStoreCompat(
            initialState = State(),
            reducer = streamReducer,
            actor = streamActor
        )
    }

    fun provide() = store
}
