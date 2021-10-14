package com.homework.coursework.customview

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.homework.coursework.R
import com.homework.coursework.utils.setMargins

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

    val textPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textAlign = Paint.Align.CENTER
        }
    }

    private var _text: String
    private val textBounds = Rect()
    private val textCoordinate = PointF()
    private val tempFontMetrics = Paint.FontMetrics()

    init {
        minimumWidth = if (minimumWidth == 0) {
            resources.getDimension(R.dimen.emoji_width).toInt()
        } else {
            minimumWidth
        }
        layoutParams = layoutParams ?: ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            resources.getDimension(R.dimen.emoji_height).toInt()
        )
        setMargins(
            left = 0,
            right = resources.getDimension(R.dimen.emoji_margin_end).toInt(),
            top = resources.getDimension(R.dimen.emoji_margin_top).toInt(),
            bottom = 0
        )
        background = background ?: ResourcesCompat.getDrawable(
            resources,
            R.drawable.bg_custom_emoji_view,
            context.theme
        )
        textPaint.typeface = ResourcesCompat.getFont(context, R.font.inter_light)
        with(context.obtainStyledAttributes(attrs, R.styleable.CustomEmojiView)) {
            _text = getString(R.styleable.CustomEmojiView_customText).orEmpty()
            textPaint.color = getColor(R.styleable.CustomEmojiView_customTextColor, Color.WHITE)
            textPaint.textSize = getDimension(
                R.styleable.CustomEmojiView_customTextSize,
                resources.getDimension(R.dimen.emoji_number_size)
            )
            recycle()
        }

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
        val drawableState = super.onCreateDrawableState(
            extraSpace + SUPPORTED_DRAWABLE_STATE.size
        )
        if (isSelected) {
            mergeDrawableStates(drawableState, SUPPORTED_DRAWABLE_STATE)
        }
        return drawableState
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(_text, textCoordinate.x, textCoordinate.y, textPaint)
    }

    companion object {
        private val SUPPORTED_DRAWABLE_STATE = intArrayOf(android.R.attr.state_selected)
    }
}
