package com.homework.coursework

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
                getString(R.string.emoji_number).toInt()
            )
        }
    }

    private fun addNewEmoji(emoji: String, number: Int) {
        val emojiView = CustomEmojiView(this).apply {
            text = "$emoji $number"
            layoutParams = ViewGroup.MarginLayoutParams(
                resources.getDimension(R.dimen.emoji_width).toInt(),
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
            setOnClickListener{
                it.isSelected = it.isSelected.not()
            }
        }
        flexboxLayout.addView(emojiView, 0)
        viewModel.customEmojiViews.add(emojiView)
    }
}