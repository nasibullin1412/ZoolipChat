package com.homework.coursework

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.imageview.ShapeableImageView
import com.homework.coursework.customview.CustomEmojiView
import com.homework.coursework.customview.CustomFlexboxLayout
import com.homework.coursework.utils.checkEmojiNumber
import com.homework.coursework.utils.setMargins
import com.homework.coursework.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var imgPlus: ShapeableImageView
    private lateinit var flexboxLayout: CustomFlexboxLayout
    private lateinit var tvUserName: TextView
    private lateinit var tvMessageContent: TextView
    private lateinit var imgAvatar: ImageView
    private lateinit var cvMessage: CardView
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
        tvUserName = findViewById(R.id.tvUserName)
        tvMessageContent = findViewById(R.id.tvMessageContent)
        imgAvatar = findViewById(R.id.imgUserAvatar)
        cvMessage = findViewById(R.id.cvMessage)
        imgAvatar.load(getString(R.string.demonstration_url)) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        tvUserName.text = getString(R.string.demonstration_name)
        tvMessageContent.text = getString(R.string.demonstration_message_content)
        cvMessage.setBackgroundResource(R.drawable.bg_custom_message)
    }

    private fun initEmojiPlus() {
        imgPlus.setOnClickListener {
            addNewEmoji(
                getString(R.string.default_emoji),
                getString(R.string.emoji_number)
            )
        }
    }

    /**
     *  The function creates and adds emoji to the layout
     *  @param emoji emoji code of reaction
     *  @param number is number of reaction
     */
    private fun addNewEmoji(emoji: String, number: String) {
        val validNumber = number.checkEmojiNumber()
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
}
