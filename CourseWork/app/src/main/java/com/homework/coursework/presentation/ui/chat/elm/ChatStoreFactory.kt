package com.homework.coursework.presentation.ui.chat.elm

import vivid.money.elmslie.core.ElmStoreCompat

class ChatStoreFactory(
    private val chatActor: ChatActor,
    private val chatReducer: ChatReducer
) {

    private val store by lazy {
        ElmStoreCompat(
            initialState = State(),
            reducer = chatReducer,
            actor = chatActor
        )
    }

    fun provide() = store
}
