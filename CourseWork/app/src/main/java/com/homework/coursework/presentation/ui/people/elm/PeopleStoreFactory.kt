package com.homework.coursework.presentation.ui.people.elm

import vivid.money.elmslie.core.ElmStoreCompat

class PeopleStoreFactory(
    private val peopleActor: PeopleActor,
    private val peopleReducer: PeopleReducer
) {

    private val store by lazy {
        ElmStoreCompat(
            initialState = State(),
            reducer = peopleReducer,
            actor = peopleActor
        )
    }

    fun provide() = store
}
