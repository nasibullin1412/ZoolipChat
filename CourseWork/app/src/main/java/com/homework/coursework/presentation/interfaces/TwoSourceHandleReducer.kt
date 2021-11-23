package com.homework.coursework.presentation.interfaces


/**
 * Костыль, так как Reducer не хочет пропускать два значения подряд
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
