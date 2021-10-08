package com.homework.coursework.customview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.homework.coursework.R
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat


class CustomEmojiView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    var text = ""
        set(value) {
            _text = value
            field = value
            requestLayout()
        }

    private var _text: String

    private val textPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textAlign = Paint.Align.CENTER
        }
    }

    private val textBounds = Rect()
    private val textCoordinate = PointF()
    private val tempFontMetrics = Paint.FontMetrics()

    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomEmojiView,
            defStyleAttr,
            defStyleRes
        )

        _text = typedArray.getString(R.styleable.CustomEmojiView_customText).orEmpty()

        textPaint.color =
            typedArray.getColor(R.styleable.CustomEmojiView_customTextColor, Color.BLACK)

        val customTypeface = ResourcesCompat.getFont(context, R.font.inter_light)
        textPaint.typeface = customTypeface

        textPaint.textSize =
            typedArray.getDimension(R.styleable.CustomEmojiView_customTextSize, DEFAULT_VALUE)

        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        textPaint.getTextBounds(_text, 0, _text.length, textBounds)

        textPaint.getTextBounds(_text, 0, _text.length, textBounds)

        val textHeight = textBounds.height()
        val textWidth = textBounds.width()

        val totalWidth = textWidth + paddingRight + paddingLeft
        val totalHeight = textHeight + paddingTop + paddingBottom

        val resultWidth = resolveSize(totalWidth, widthMeasureSpec)
        val resultHeight = resolveSize(totalHeight, heightMeasureSpec)
        setMeasuredDimension(resultWidth, resultHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        textPaint.getFontMetrics(tempFontMetrics)
        textCoordinate.x = w / 2f
        textCoordinate.y = h / 2f + textBounds.height() / 2 - tempFontMetrics.descent
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + SUPPORTED_DRAWABLE_STATE.size)
        if (isSelected) {
            mergeDrawableStates(drawableState, SUPPORTED_DRAWABLE_STATE)
        }
        return drawableState
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(_text, textCoordinate.x, textCoordinate.y, textPaint)
    }

    companion object {
        private const val DEFAULT_VALUE = 76f
        private val SUPPORTED_DRAWABLE_STATE = intArrayOf(android.R.attr.state_selected)
    }

}