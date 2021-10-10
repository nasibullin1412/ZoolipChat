package com.homework.coursework

import android.icu.text.CompactDecimalFormat
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.imageview.ShapeableImageView
import com.homework.coursework.customview.CustomEmojiView
import com.homework.coursework.customview.CustomFlexboxLayout
import com.homework.coursework.utils.setMargins
import com.homework.coursework.viewmodel.MainViewModel
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var imgPlus: ShapeableImageView
    private lateinit var flexboxLayout: CustomFlexboxLayout
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_main)
        initViewBlock()
        initEmojiPlus()
    }

    private fun initViewBlock() {
        imgPlus = findViewById(R.id.imgPlus)
        flexboxLayout = findViewById(R.id.fblEmoji)
    }


    private fun initEmojiPlus() {
        imgPlus.setOnClickListener {
            addNewEmoji(
                getString(R.string.default_emoji),
                getString(R.string.emoji_number)
            )
        }
    }

    private fun addNewEmoji(emoji: String, number: String) {
        val validNumber = checkEmojiNumber(number)
        val emojiView = CustomEmojiView(this).apply {
            text = "$emoji $validNumber"
            minimumWidth = resources.getDimension(R.dimen.emoji_width).toInt()
            layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                resources.getDimension(R.dimen.emoji_height).toInt()
            )
            setMargins(
                left = 0,
                right = resources.getDimension(R.dimen.emoji_margin_end).toInt(),
                top = resources.getDimension(R.dimen.emoji_margin_top).toInt(),
                bottom = 0
            )

            background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_custom_emoji_view,
                theme
            )
            textPaint.textSize = resources.getDimension(R.dimen.emoji_number_size)
            textPaint.color = ResourcesCompat.getColor(
                resources,
                R.color.white,
                theme
            )
            setOnClickListener {
                it.isSelected = it.isSelected.not()
            }
        }
        flexboxLayout.addView(emojiView, 0)
        viewModel.customEmojiViews.add(emojiView)
    }

    private fun checkEmojiNumber(number: String): String {
        val compactDecimalFormat: CompactDecimalFormat =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                CompactDecimalFormat.getInstance(Locale.US, CompactDecimalFormat.CompactStyle.SHORT)
            } else {
                return number
            }
        return compactDecimalFormat.format(number.toInt())
    }
}