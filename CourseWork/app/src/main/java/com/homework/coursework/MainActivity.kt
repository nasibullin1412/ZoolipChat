package com.homework.coursework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.homework.coursework.adapters.MessageAdapter
import com.homework.coursework.customview.CustomFlexboxLayout
import com.homework.coursework.data.MessageData
import com.homework.coursework.data.UserData
import com.homework.coursework.databinding.ActvityMainBinding
import com.homework.coursework.databinding.BottomSheetBinding
import com.homework.coursework.interfaces.MessageItemCallback
import com.homework.coursework.utils.setMargins
import com.homework.coursework.utils.toDateMap
import com.homework.coursework.viewmodel.MainViewModel
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(), MessageItemCallback {
    private lateinit var binding: ActvityMainBinding
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private val viewModel: MainViewModel by viewModels()
    private val listMessage = listOf(
        MessageData(
            messageId = 0,
            userData = UserData(1, "Марк Цукерберг", "https://clck.ru/YDyYU"),
            messageContent = "Лол, видел как всё положил",
            emojis = arrayListOf(),
            date = "1 Фев"
        ),
        MessageData(
            messageId = 1,
            userData = UserData(1, "Марк Цукерберг", "https://clck.ru/YDyYU"),
            messageContent = "Лови гостей, дурик",
            emojis = arrayListOf(),
            date = "1 Фев"
        ),
        MessageData(
            messageId = 2,
            userData = UserData(
                0, "Павел Дуров",
                "https://clck.ru/YEN9d"
            ),
            messageContent = "Маму люби",
            emojis = arrayListOf(),
            date = "1 Фев"
        ),
        MessageData(
            messageId = 3,
            userData = UserData(1, "Марк Цукерберг", "https://clck.ru/YDyYU"),
            messageContent = "Даю тебе фору",
            emojis = arrayListOf(),
            date = "2 Фев"
        ),
        MessageData(
            messageId = 4,
            userData = UserData(
                0, "Павел Дуров",
                "https://clck.ru/YEN9d"
            ),
            messageContent = "Суп посоли",
            emojis = arrayListOf(),
            date = "2 Фев"
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActvityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycleView()
        initBottomDialog()
    }

    private fun initBottomDialog() {
        bottomSheetDialog = BottomSheetDialog(this).apply {
            setContentView(R.layout.bottom_sheet)
            findViewById<CardView>(R.id.cvEmojiBottom)?.apply {
                background = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.sh_bottom_sheet,
                    theme
                )
            }

        }
        val emojiCodes = resources.getStringArray(R.array.emoji_codes)
        val flbLayout = bottomSheetDialog.findViewById<CustomFlexboxLayout>(R.id.fblBottomSheet)
        for (emoji in emojiCodes) {
            flbLayout?.addView(
                TextView(this).apply {
                    text = emoji.toString()
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
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }
            )
        }
    }


    private fun initRecycleView() {
        with(binding.rvMessage) {
            messageAdapter = MessageAdapter(1)
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        messageAdapter.initListener(this)
        messageAdapter.dates = listMessage.toDateMap()
        messageAdapter.submitList(listMessage.toList())
    }

    override fun getBottomSheet(): Boolean {
        bottomSheetDialog.show()
        return true
    }


    /**
     *  The function creates and adds emoji to the layout
     *  @param emoji emoji code of reaction
     *  @param number is number of reaction

    private fun addNewEmoji(emoji: String, number: String) {
    val validNumber = number.checkEmojiNumber()
    val emojiView = CustomEmojiView(this).apply {
    text = "$emoji $validNumber"
    setOnClickListener {
    it.isSelected = it.isSelected.not()
    }
    }
    flexboxLayout.addView(emojiView, 0)
    viewModel.customEmojiViews.add(emojiView)
    }*/
}
