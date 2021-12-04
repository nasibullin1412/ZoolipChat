package com.homework.coursework.presentation.utils

import android.icu.text.CompactDecimalFormat
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.imageview.ShapeableImageView
import com.homework.coursework.R
import com.homework.coursework.presentation.FragmentFactory
import com.homework.coursework.presentation.adapter.data.EmojiItem
import com.homework.coursework.presentation.adapter.data.MessageItem
import com.homework.coursework.presentation.customview.CustomEmojiView
import com.homework.coursework.presentation.customview.CustomFlexboxLayout
import com.homework.coursework.presentation.interfaces.MessageItemCallback
import kotlinx.serialization.ExperimentalSerializationApi
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

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

fun Fragment.showToast(message: String?) {
    Log.e("Toast", message ?: "")
    when {
        message.isNullOrEmpty() -> {
            showToast("Error")
        }
        else -> Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}

/**
 * init textview, which add in customFlexbox layout in bottom sheet
 * @param emojiCode is emoji code, which need to add
 */
fun TextView.initEmojiToBottomSheet(emojiCode: String) {
    text = emojiCode
    layoutParams = ViewGroup.MarginLayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    setMargins(
        left = 0,
        right = resources.getDimension(R.dimen.emoji_margin_bottom_end).toInt(),
        top = resources.getDimension(R.dimen.emoji_margin_top).toInt(),
        bottom = 0
    )
    textSize = resources.getDimension(R.dimen.emoji_text_size)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setTextColor(resources.getColor(R.color.white, context.theme))
    } else {
        setTextColor(resources.getColor(R.color.white))
    }
    textAlignment = View.TEXT_ALIGNMENT_CENTER
}

/**
 * Add CustomEmojiView to CustomFlexboxLayout
 * @param emoji is emoji, which was added
 * @param idx is idx of emoji, need for listenner
 * @param listener is listener for callback
 */
fun CustomFlexboxLayout.addEmoji(emoji: EmojiItem, idx: Int, listener: MessageItemCallback) {
    val validNumber = emoji.emojiNumber.toString().checkEmojiNumber()
    val emojiView = CustomEmojiView(context).apply {
        val emojiCodeInt = emoji.emojiCode.toIntWithValid()
        text = "${Emoji.toEmoji(emojiCodeInt)} $validNumber"
        isSelected = emoji.isCurrUserReacted
        setOnClickListener {
            it.isSelected = it.isSelected.not()
            listener.onEmojiClick(emoji, idx)
        }
    }
    addView(emojiView, 0)
}

fun String.toIntWithValid(): Int {
    return try {
        if (length < 6) {
            return toInt(16)
        }
        substring(0, 4).toInt(16)
    } catch (e: NumberFormatException) {
        0
    }
}

/**
 * Add PlusImgView to CustomFlexboxLayout
 * @param idx is idx of plus, need to listener
 * @param listener is listener for callback
 */
fun CustomFlexboxLayout.addPlusImgView(idx: Int, listener: MessageItemCallback) {
    val plusImgView = ShapeableImageView(context).apply {
        layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            resources.getDimension(R.dimen.emoji_height).toInt()
        )
        setMargins(
            left = 0,
            right = 0,
            top = resources.getDimension(R.dimen.img_margin_top).toInt(),
            bottom = 0
        )
        background = ResourcesCompat.getDrawable(
            resources,
            R.drawable.bg_custom_emoji_view,
            context.theme
        )
        minimumWidth = resources.getDimension(R.dimen.img_min_width).toInt()
        setImageResource(R.drawable.ic_plus)
        val padding = resources.getDimension(R.dimen.img_min_content_padding).toInt()
        post {
            setContentPadding(
                padding,
                padding,
                padding,
                padding
            )
        }
        setOnClickListener {
            listener.getBottomSheet(idx)
        }
    }
    addView(
        plusImgView
    )
}

/**
 * add emoji to CustomFlexboxLayout logic
 * @param messageItem is message model
 * @param idx is idx for emoji callback listener
 * @param listener is listener for callback
 */
fun CustomFlexboxLayout.emojiLogic(
    messageItem: MessageItem,
    idx: Int,
    listener: MessageItemCallback
) {
    if (messageItem.emojis.isEmpty()) {
        removeAllViews()
        return
    }
    if (childCount == 0) {
        addPlusImgView(idx, listener)
    }
    val delta = messageItem.emojis.size - childCount + 1
    when (delta) {
        1 -> addEmoji(
            emoji = messageItem.emojis.last(),
            idx = idx,
            listener = listener
        )
        else -> {
            removeAllViews()
            addPlusImgView(idx, listener)
            for (emoji in messageItem.emojis) {
                addEmoji(emoji, idx = idx, listener = listener)
            }
        }
    }
}

fun AppCompatActivity.addChannelFragment(fragmentFactory: FragmentFactory, tag: String) {
    if (supportFragmentManager.backStackEntryCount != 0) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
    supportFragmentManager.beginTransaction()
        .replace(R.id.nav_host_fragment, fragmentFactory.fragment, tag)
        .commit()
}

@ExperimentalSerializationApi
fun AppCompatActivity.addFragment(fragmentFactory: FragmentFactory) {
    val tag = fragmentFactory.fragmentTag.value
    if (fragmentFactory.fragmentTag == FragmentTag.CHANNEL_FRAGMENT_TAG) {
        addChannelFragment(fragmentFactory = fragmentFactory, tag = tag)
        return
    }
    if (fragmentFactory.fragmentTag == FragmentTag.AUTH_FRAGMENT_TAG) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
    supportFragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    supportFragmentManager.beginTransaction()
        .replace(R.id.nav_host_fragment, fragmentFactory.fragment, tag)
        .addToBackStack(tag)
        .commit()
}

fun Long.toStringDate(): String {
    val date = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this * 1000),
        getZone()
    )
    val day = date.format(DateTimeFormatter.ofPattern("dd"))
    val month = date.month.getDisplayName(TextStyle.SHORT, Locale("ru"))
    return "$day $month"
}

internal fun getZone(): ZoneId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    TimeZone.getDefault().toZoneId()
} else {
    Clock.systemDefaultZone().zone
}

fun ShimmerFrameLayout.off() {
    stopShimmer()
    hideShimmer()
    visibility = View.GONE
}

fun Int.getValueByCondition(condition: Boolean, second: Int) = if (condition) {
    this
} else {
    second
}
