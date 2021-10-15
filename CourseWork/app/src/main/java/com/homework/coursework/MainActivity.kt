package com.homework.coursework

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.homework.coursework.adapters.MessageAdapter
import com.homework.coursework.data.MessageData
import com.homework.coursework.data.UserData
import com.homework.coursework.databinding.ActvityMainBinding
import com.homework.coursework.utils.toDateMap
import com.homework.coursework.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActvityMainBinding
    private lateinit var messageAdapter: MessageAdapter
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
    }

    private fun initRecycleView() {
        with(binding.rvMessage) {
            messageAdapter = MessageAdapter(1)
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        messageAdapter.dates = listMessage.toDateMap()
        messageAdapter.submitList(listMessage.toList())
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
