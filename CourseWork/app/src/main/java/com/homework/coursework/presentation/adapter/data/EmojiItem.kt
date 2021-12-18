package com.homework.coursework.presentation.adapter.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmojiItem(
    val emojiCode: String,
    var emojiNumber: Int,
    val emojiName: String,
    var isCurrUserReacted: Boolean
): Parcelable
