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
import com.homework.coursework.presentation.adapter.data.*
import com.homework.coursework.presentation.customview.CustomEmojiView
import com.homework.coursework.presentation.customview.CustomFlexboxLayout
import com.homework.coursework.presentation.discuss.TopicDiscussionFragment
import com.homework.coursework.presentation.interfaces.MessageItemCallback
import com.homework.coursework.presentation.profile.ProfileFragment
import com.homework.coursework.presentation.stream.StreamFragment
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
 * On existed emoji check logic. Increase or decrease emoji number
 * @param idx is index of emoji in emoji list
 */
fun ArrayList<EmojiItem>.checkExistedEmoji(idx: Int) {
    if (this[idx].isCurrUserReacted) {
        this[idx].isCurrUserReacted = false
        if (this[idx].emojiNumber > 1) {
            this[idx].emojiNumber -= 1
            return
        }
        removeAt(idx)
    } else {
        this[idx].emojiNumber += 1
        this[idx].isCurrUserReacted = true
    }
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

/**
 * add date to recycler list
 * @param date is string with date
 */
fun ArrayList<DiscussItem>.addDate(date: String) {
    if (lastIndex == -1 || this[lastIndex].messageItem?.date?.toStringDate() == date) {
        return
    }
    val curIdx = lastIndex + 1
    add(
        DiscussItem(
            id = curIdx,
            messageItem = null,
            date = date
        )
    )
}

/**
 * add message to recycler list
 * @param messageItemList is list with new messages
 */
fun ArrayList<DiscussItem>.addMessageItem(messageItemList: List<MessageItem>) {
    val idx = lastIndex + 1
    addAll(
        messageItemList.mapIndexed { index, messageItem ->
            DiscussItem(
                id = idx + index,
                messageItem = messageItem,
                date = null
            )
        }
    )
}

@ExperimentalSerializationApi
fun FragmentTag.fragmentByTag(topic: TopicItem? = null, stream: StreamItem? = null): Fragment =
    when (this) {
        FragmentTag.CHANNEL_FRAGMENT_TAG -> StreamFragment()
        FragmentTag.PROFILE_FRAGMENT_TAG -> ProfileFragment()
        FragmentTag.PEOPLE_FRAGMENT_TAG -> ProfileFragment()
        FragmentTag.TOPIC_DISCUSSION_FRAGMENT_TAG -> {
            if (topic != null && stream != null) {
                TopicDiscussionFragment.newInstance(topic, stream)
            } else {
                throw IllegalArgumentException("topic and stream required")
            }
        }
    }

fun AppCompatActivity.addFragment(fragment: Fragment, tag: FragmentTag) {
    if (tag == FragmentTag.CHANNEL_FRAGMENT_TAG) {
        if (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStack(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        } else {
            supportFragmentManager.beginTransaction()
                .add(R.id.nav_host_fragment, fragment, tag.value)
                .commit()
        }
        return
    }
    supportFragmentManager.popBackStack(tag.value, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    supportFragmentManager.beginTransaction()
        .replace(R.id.nav_host_fragment, fragment, tag.value)
        .addToBackStack(tag.value)
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
