package com.homework.coursework.customview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.marginRight
import androidx.core.view.marginTop

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
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, totalHeight)
            tempWidth += child.measuredWidth + child.marginRight
            if (tempWidth > widthSizeMax) {
                tempWidth = 0
                totalHeight += child.measuredHeight + child.marginTop
            }
            totalHeight = maxOf(totalHeight, child.measuredHeight)
        }
        val resultWidth = resolveSize(widthSizeMax, widthMeasureSpec)
        val resultHeight = resolveSize(totalHeight, heightMeasureSpec)
        setMeasuredDimension(resultWidth, resultHeight)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentBottom: Int
        var currentRight: Int
        var currentLeft = 0
        var currentTop = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            currentRight = currentLeft + child.measuredWidth
            if (currentRight > width) {
                currentLeft = 0
                currentRight = child.measuredWidth
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
}