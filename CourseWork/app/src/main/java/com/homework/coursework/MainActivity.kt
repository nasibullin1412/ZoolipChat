package com.homework.coursework

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.homework.coursework.adapters.MessageAdapter
import com.homework.coursework.customview.CustomFlexboxLayout
import com.homework.coursework.data.EmojiData
import com.homework.coursework.data.MessageData
import com.homework.coursework.data.UserData
import com.homework.coursework.databinding.ActvityMainBinding
import com.homework.coursework.interfaces.MessageItemCallback
import com.homework.coursework.utils.*
import com.homework.coursework.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), MessageItemCallback {
    private lateinit var binding: ActvityMainBinding
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private var listMessage: ArrayList<MessageData> = testList
    private var selectedMessageId = DEFAULT_MESSAGE_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActvityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        initRecycleView()
        initBottomDialog()
        initButtonListener()
        initEditText()
    }

    private fun initButtonListener() {
        binding.imgSend.setOnClickListener {
            updateMessageRecycler(binding.etMessage.text.toString())
        }
    }

    private fun updateMessageRecycler(messageContent: String) {
        listMessage.addMessageData(messageContent)
        messageAdapter.submitList(listMessage)
    }

    private fun initEditText() {
        binding.imgSend.background = ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_vector,
            theme
        )
        binding.etMessage.apply {
            addTextChangedListener {
                binding.imgSend.background = ResourcesCompat.getDrawable(
                    resources,
                    selectIcon(text.toString()),
                    theme
                )
            }
        }
    }

    private fun selectIcon(text: String): Int = if (text.isEmpty()) {
        R.drawable.ic_vector
    } else {
        R.drawable.ic_vector_send
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
                    initEmojiToBottomSheet(emoji.toString())
                    setOnClickListener { onEmojiClicked(text.toString()) }
                }
            )
        }
    }

    private fun onEmojiClicked(emojiCode: String) {
        if (selectedMessageId == DEFAULT_MESSAGE_ID) {
            throw IllegalArgumentException("selectedMessageId required")
        }
        val idx = listMessage[selectedMessageId].emojis.indexOfFirst { it.emojiCode == emojiCode }
        if (idx == DEFAULT_MESSAGE_ID) {
            listMessage[selectedMessageId].emojis.add(
                EmojiData(
                    emojiCode = emojiCode,
                    emojiNumber = 1,
                    isCurrUserReacted = true
                )
            )
        }
        else{
            listMessage[selectedMessageId].emojis.checkExistedEmoji(idx)
        }
        messageAdapter.notifyItemChanged(selectedMessageId)
        selectedMessageId = DEFAULT_MESSAGE_ID
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }


    private fun initRecycleView() {
        with(binding.rvMessage) {
            messageAdapter = MessageAdapter(1)
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        messageAdapter.initListener(this)
        messageAdapter.dates = listMessage.toDateMap()
        messageAdapter.submitList(listMessage.toMutableList())
    }

    override fun getBottomSheet(messageId: Int): Boolean {
        selectedMessageId = messageId
        bottomSheetDialog.show()
        return true
    }

    override fun onEmojiClick(emojiCode: String, idx: Int): Boolean {
        selectedMessageId = idx
        onEmojiClicked(emojiCode)
        return true
    }

    companion object {
        const val DEFAULT_MESSAGE_ID = -1
        const val CURR_USER_ID = 1
        const val CURR_USER_NAME = "Марк Цукерберг"
        const val CURR_USER_AVATAR_URL = "https://clck.ru/YDyYU"
    }
}
