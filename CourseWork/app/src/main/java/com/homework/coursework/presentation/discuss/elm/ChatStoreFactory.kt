package com.homework.coursework.presentation.discuss.elm

import vivid.money.elmslie.core.ElmStoreCompat

class ChatStoreFactory(private val chatActor: ChatActor) {

    private val store by lazy {
        ElmStoreCompat(
            initialState = State(),
            reducer = Reducer(),
            actor = chatActor
        )
    }

    fun provide() = store
}
