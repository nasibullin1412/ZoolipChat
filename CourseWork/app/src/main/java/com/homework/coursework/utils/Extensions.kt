package com.homework.coursework.utils

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
import com.google.android.material.imageview.ShapeableImageView
import com.homework.coursework.R
import com.homework.coursework.customview.CustomEmojiView
import com.homework.coursework.customview.CustomFlexboxLayout
import com.homework.coursework.data.BaseItem
import com.homework.coursework.data.EmojiData
import com.homework.coursework.data.MessageData
import com.homework.coursework.interfaces.MessageItemCallback
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

fun AppCompatActivity.showToast(message: String?) {
    Log.e("Toast", message ?: "")
    when {
        message.isNullOrEmpty() -> {
            showToast("Error")
        }
        else -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
fun ArrayList<EmojiData>.checkExistedEmoji(idx: Int) {
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
fun CustomFlexboxLayout.addEmoji(emoji: EmojiData, idx: Int, listener: MessageItemCallback) {
    val validNumber = emoji.emojiNumber.toString().checkEmojiNumber()
    val emojiView = CustomEmojiView(context).apply {
        text = "${emoji.emojiCode} $validNumber"
        isSelected = true
        setOnClickListener {
            it.isSelected = it.isSelected.not()
            listener.onEmojiClick(emoji.emojiCode, idx)
        }
    }
    addView(emojiView, 0)
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
 * @param messageData is message model
 * @param idx is idx for emoji callback listener
 * @param listener is listener for callback
 */
fun CustomFlexboxLayout.emojiLogic(
    messageData: MessageData,
    idx: Int,
    listener: MessageItemCallback
) {
    if (messageData.emojis.isEmpty()) {
        removeAllViews()
        return
    }
    if (childCount == 0) {
        addPlusImgView(idx, listener)
    }
    val delta = messageData.emojis.size - childCount + 1
    when (delta) {
        1 -> addEmoji(
            emoji = messageData.emojis.last(),
            idx = idx,
            listener = listener
        )
        else -> {
            removeAllViews()
            addPlusImgView(idx, listener)
            for (emoji in messageData.emojis) {
                addEmoji(emoji, idx = idx, listener = listener)
            }
        }
    }
}

/**
 * add date to recycler list
 * @param date is string with date
 */
fun ArrayList<BaseItem>.addDate(date: String) {
    if (lastIndex != -1 && this[lastIndex].messageData?.date == date) {
        return
    }
    val curIdx = lastIndex + 1
    this.add(
        BaseItem(
            id = curIdx,
            messageData = null,
            date = date
        )
    )
}

/**
 * add message to recycler list
 * @param messageDataList is list with new messages
 */
fun ArrayList<BaseItem>.addMessageData(messageDataList: List<MessageData>) {
    val idx = lastIndex
    this.addAll(
        messageDataList.mapIndexed { index, messageData ->
            BaseItem(
                id = idx + index,
                messageData = messageData,
                date = null
            )
        }
    )
}
