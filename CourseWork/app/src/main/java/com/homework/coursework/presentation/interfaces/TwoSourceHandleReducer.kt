package com.homework.coursework.presentation.interfaces


/**
 * Two data source reducer handler
 */
interface TwoSourceHandleReducer<in I, out O> {

    //need to reset after stop fragment
    var isSecondError: Boolean

    fun handleResult(event: I): O?

    fun isWithError(event: I): Boolean

    fun isSecondErrorChange(): Boolean {
        val temp = isSecondError
        isSecondError = isSecondError.not()
        return temp
    }
}
