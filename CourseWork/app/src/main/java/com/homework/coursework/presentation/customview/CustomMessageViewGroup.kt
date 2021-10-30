package com.homework.coursework.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.homework.coursework.R
import com.homework.coursework.presentation.utils.layoutChildren

class CustomMessageViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    init {
        inflate(context, R.layout.custom_message_view_group, this)
    }

    private var widthMeasureSpec: Int = 0
    private var heightMeasureSpec: Int = 0

    /**
     * Calculating the size that the view brings to the total size
     * @param view is child view
     * @param currentWidth is current calculated width
     * @param currentHeight is current calculated height
     */
    private fun calculateSize(
        view: View,
        currentWidth: Int,
        currentHeight: Int,
        isRight: Boolean
    ): Pair<Int, Int> {
        val margin = view.layoutParams as MarginLayoutParams
        measureChildWithMargins(
            view,
            widthMeasureSpec,
            0,
            heightMeasureSpec,
            0
        )
        val width: Int
        val height: Int
        if (isRight) {
            width = view.measuredWidth + margin.marginStart + margin.marginEnd + currentWidth
            height = maxOf(currentHeight, view.measuredHeight)
        } else {
            height = margin.topMargin + view.measuredHeight + currentHeight
            width = maxOf(
                currentWidth,
                view.measuredWidth + margin.marginStart
            )
        }
        return Pair(width, height)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        this.widthMeasureSpec = widthMeasureSpec
        this.heightMeasureSpec = heightMeasureSpec
        var totalWidth = 0
        var totalHeight = 0
        for (i in 0 until childCount) {
            val sizes = calculateSize(
                view = getChildAt(i),
                currentWidth = totalWidth,
                currentHeight = totalHeight,
                isRight = i != DEFAULT_BOTTOM
            )
            totalWidth = sizes.first
            totalHeight = sizes.second
        }
        val resultWidth = resolveSize(totalWidth + paddingRight + paddingLeft, widthMeasureSpec)
        val resultHeight = resolveSize(totalHeight + paddingTop + paddingBottom, heightMeasureSpec)
        setMeasuredDimension(resultWidth, resultHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var prevChild: View? = null
        val viewGroupPaddingLeft = paddingLeft
        val viewGroupPaddingTop = paddingTop
        for (i in 0 until DEFAULT_BOTTOM) {
            prevChild = getChildAt(i).apply {
                val left = marginStart + (prevChild?.right ?: viewGroupPaddingLeft)
                val top = marginTop + viewGroupPaddingTop
                layoutChildren(left, top)
            }
        }
        getChildAt(DEFAULT_BOTTOM).apply {
            val left = marginStart
            val top = marginTop + (prevChild?.bottom ?: viewGroupPaddingTop)
            layoutChildren(left, top)
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
        const val DEFAULT_BOTTOM = 2
    }
}
