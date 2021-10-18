package com.homework.coursework.customview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.homework.coursework.data.EmojiData
import com.homework.coursework.interfaces.MessageItemCallback
import com.homework.coursework.utils.checkEmojiNumber

class CustomFlexboxLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSizeMax = MeasureSpec.getSize(widthMeasureSpec)
        var tempWidth = 0
        var totalHeight = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChildWithMargins(
                child, widthMeasureSpec,
                0, heightMeasureSpec, totalHeight
            )
            val widthNeed = getWidth(child.minimumWidth, child.measuredWidth)
            tempWidth += widthNeed + child.marginRight
            if (tempWidth > widthSizeMax) {
                tempWidth = widthNeed
                totalHeight += child.measuredHeight + child.marginTop
            }
            totalHeight = maxOf(totalHeight, child.measuredHeight)
        }
        val resultWidth = resolveSize(widthSizeMax, widthMeasureSpec)
        val resultHeight = resolveSize(totalHeight, heightMeasureSpec)
        setMeasuredDimension(resultWidth, resultHeight)
    }

    /**
     * @param min is minWidth attr value
     * @param measure is view measure width
     * @return minWidth attribute value if min < measure
     */
    private fun getWidth(min: Int, measure: Int): Int =
        if (min < measure) {
            measure
        } else {
            min
        }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentBottom: Int
        var currentRight: Int
        var currentLeft = 0
        var currentTop = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val widthNeed = getWidth(child.minimumWidth, child.measuredWidth)
            currentRight = currentLeft + widthNeed
            if (currentRight > width) {
                currentLeft = 0
                currentRight = widthNeed
                currentTop += child.measuredHeight + child.marginTop
            }
            currentBottom = currentTop + child.measuredHeight
            child.layout(
                currentLeft,
                currentTop,
                currentRight,
                currentBottom
            )
            currentLeft = child.right + child.marginRight
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun checkLayoutParams(p: LayoutParams): Boolean {
        return p is MarginLayoutParams
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        return MarginLayoutParams(p)
    }

    companion object {
        private fun addEmoji(
            emoji: EmojiData,
            idx: Int,
            context: Context,
            listener: MessageItemCallback
        ): CustomEmojiView {
            val validNumber = emoji.emojiNumber.toString().checkEmojiNumber()
            return CustomEmojiView(context).apply {
                text = "${emoji.emojiCode} $validNumber"
                isSelected = true
                setOnClickListener {
                    it.isSelected = it.isSelected.not()
                    listener.onEmojiClick(emoji.emojiCode, idx)
                }
            }
        }
    }
}
