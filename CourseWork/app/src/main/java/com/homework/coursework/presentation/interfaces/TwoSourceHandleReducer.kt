package com.homework.coursework.presentation.interfaces


/**
 * Костыль, так как Reducer не хочет пропускать два значения подряд
 */
interface TwoSourceHandleReducer<In, Out> {

    //need to reset after stop fragment
    var isSecondError: Boolean

    fun handleResult(event: In): Out?

    fun isWithError(event: In): Boolean

    fun isSecondErrorChange(): Boolean {
        val temp = isSecondError
        isSecondError = isSecondError.not()
        return temp
    }
}