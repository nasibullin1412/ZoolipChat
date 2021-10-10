package com.homework.coursework.customview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.marginTop
import com.homework.coursework.R

class CustomMessageViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    init {
        inflate(context, R.layout.custom_message_view_group, this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val imageView = getChildAt(0)
        val messageViewCard = getChildAt(1)
        val customFlexboxLayout = getChildAt(2)
        messageViewCard.setBackgroundResource(R.drawable.bg_custom_message)
        var totalWidth = 0
        var totalHeight = 0
        measureChildWithMargins(
            imageView,
            widthMeasureSpec,
            0,
            heightMeasureSpec,
            0
        )
        val imageMargin = imageView.layoutParams as MarginLayoutParams
        totalWidth += imageView.measuredWidth + imageMargin.marginStart + imageMargin.marginStart
        totalHeight = maxOf(totalHeight, imageView.measuredHeight)
        measureChildWithMargins(
            messageViewCard,
            widthMeasureSpec,
            imageView.measuredWidth,
            heightMeasureSpec,
            0
        )
        val viewCardMessage = messageViewCard.layoutParams as MarginLayoutParams
        totalWidth += messageViewCard.measuredWidth + viewCardMessage.marginStart +
                viewCardMessage.marginEnd
        totalHeight = maxOf(totalHeight, messageViewCard.measuredHeight)
        measureChildWithMargins(
            customFlexboxLayout,
            widthMeasureSpec,
            messageViewCard.measuredWidth,
            heightMeasureSpec,
            messageViewCard.measuredHeight
        )
        val flexboxMargin = customFlexboxLayout.layoutParams as MarginLayoutParams
        totalWidth = maxOf(
            totalWidth,
            customFlexboxLayout.measuredWidth + flexboxMargin.marginStart +
                    imageView.measuredWidth
        )
        totalHeight += customFlexboxLayout.marginTop + customFlexboxLayout.measuredHeight
        val resultWidth = resolveSize(totalWidth + paddingRight + paddingLeft, widthMeasureSpec)
        val resultHeight = resolveSize(totalHeight + paddingTop + paddingBottom, heightMeasureSpec)
        setMeasuredDimension(resultWidth, resultHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val imageView = getChildAt(0)
        val textView = getChildAt(1)
        val customFlexboxLayout = getChildAt(2)

        val marginImage = (imageView.layoutParams as MarginLayoutParams)

        var left = paddingLeft + marginImage.marginStart
        var top = paddingTop + marginImage.topMargin
        var right = left + imageView.measuredWidth
        var bottom = top + imageView.measuredHeight

        imageView.layout(
            left,
            top,
            right,
            bottom
        )


        val marginText = (textView.layoutParams as MarginLayoutParams)
        left = imageView.right + marginText.marginStart
        top = paddingTop + marginText.topMargin
        right = left + textView.measuredWidth
        bottom = top + textView.measuredHeight

        textView.layout(
            left,
            top,
            right,
            bottom
        )

        val flexboxMargin = (customFlexboxLayout.layoutParams as MarginLayoutParams)
        left = imageView.right + flexboxMargin.marginStart
        top = textView.bottom + flexboxMargin.topMargin
        right = left + customFlexboxLayout.measuredWidth
        bottom = top + customFlexboxLayout.measuredHeight

        customFlexboxLayout.layout(
            left,
            top,
            right,
            bottom
        )
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