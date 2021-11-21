package com.homework.coursework.data.dto

class Anchor(
    private val anchorStr: String? = null,
    private val anchorInt: Int? = null
) {
    fun getAnchor() = if (anchorStr.isNullOrEmpty()) {
        anchorStr
    } else {
        anchorInt
    }
}