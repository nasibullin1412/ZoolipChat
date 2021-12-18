package com.homework.coursework.presentation.ui.editmessage.elm

import vivid.money.elmslie.core.ElmStoreCompat

class EditMessageStoreFactory(
    editMessageActor: EditMessageActor,
    editMessageReducer: EditMessageReducer
) {
    private val store by lazy {
        ElmStoreCompat(
            initialState = State(),
            reducer = editMessageReducer,
            actor = editMessageActor
        )
    }

    fun provide() = store
}
