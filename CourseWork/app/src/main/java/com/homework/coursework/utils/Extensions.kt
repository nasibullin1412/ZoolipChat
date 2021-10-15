package com.homework.coursework.utils

import android.icu.text.CompactDecimalFormat
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.homework.coursework.data.MessageData
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

/**
 * set margin to view with layout length
 * @param left is left length
 * @param top is top length
 * @param right is right length
 * @param bottom is bottom length
 */
fun View.setMargins(
    left: Int = this.marginLeft,
    top: Int = this.marginTop,
    right: Int = this.marginRight,
    bottom: Int = this.marginBottom,
) {
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
        setMargins(left, top, right, bottom)
    }
}

/**
 *  converts large numbers to short format
 *  @return short number format
 */
fun String.checkEmojiNumber(): String {
    val compactDecimalFormat: CompactDecimalFormat =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            CompactDecimalFormat.getInstance(Locale.US, CompactDecimalFormat.CompactStyle.SHORT)
        } else {
            return this
        }
    return compactDecimalFormat.format(this.toInt())
}

/**
 * sets view parameters on the layout
 * @param left is left side
 * @param top is top side
 */
fun View.layoutChildren(left: Int, top: Int) {
    val right = left + this.measuredWidth
    val bottom = top + this.measuredHeight
    this.layout(
        left,
        top,
        right,
        bottom
    )
}

fun List<MessageData>.toDateMap(): Map<String, Int> {
    return this.map{
        it.date
    }.toSet().mapIndexed {index, date ->
        date to index + 1
    }.toMap()
}
