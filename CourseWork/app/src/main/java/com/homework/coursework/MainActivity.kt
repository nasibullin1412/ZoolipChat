package com.homework.coursework

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.homework.coursework.adapters.MessageAdapter
import com.homework.coursework.customview.CustomFlexboxLayout
import com.homework.coursework.data.BaseItem
import com.homework.coursework.data.EmojiData
import com.homework.coursework.data.MessageData
import com.homework.coursework.data.UserData
import com.homework.coursework.databinding.ActvityMainBinding
import com.homework.coursework.interfaces.MessageItemCallback
import com.homework.coursework.utils.*

class MainActivity : AppCompatActivity(), MessageItemCallback {
    private lateinit var binding: ActvityMainBinding
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private var listMessage: ArrayList<MessageData> = testList
    private var listRecyclerView: ArrayList<BaseItem> = arrayListOf()
    private var messageIdx = DEFAULT_MESSAGE_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActvityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateMessage(testList)
        initViews()
    }

    /**
     * update recycler view with new message
     * @param newList is list with new MessageData
     */
    private fun updateMessage(newList: ArrayList<MessageData>) {
        val groupedMessage = newList.groupBy { it.date }
        with(groupedMessage) {
            for (date in keys) {
                listRecyclerView.addDate(date)
                listRecyclerView.addMessageData(
                    this[date]
                        ?: throw IllegalArgumentException("message required")
                )
            }
        }
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
            binding.etMessage.setText("")
        }
    }

    private fun updateMessageRecycler(messageContent: String) {
        updateMessage(
            arrayListOf(
                MessageData(
                    messageId = listRecyclerView.last().messageData?.messageId
                        ?: throw IllegalArgumentException("messageData required"),
                    userData = UserData(
                        id = CURR_USER_ID,
                        name = CURR_USER_NAME,
                        avatarUrl = CURR_USER_AVATAR_URL
                    ),
                    messageContent = messageContent,
                    emojis = arrayListOf(),
                    date = CURR_USER_DATE
                )
            )
        )
        messageAdapter.submitList(listRecyclerView)
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

    /**
     * In Emoji click callback, which add new emoji or increase and decrease existed
     * emoji number
     * @param emojiCode is clicked emoji code
     */
    private fun onEmojiClicked(emojiCode: String) {
        if (messageIdx == DEFAULT_MESSAGE_ID) {
            throw IllegalArgumentException("selectedMessageId required")
        }
        with(listRecyclerView[messageIdx].messageData) {
            val idx = this?.emojis?.indexOfFirst { it.emojiCode == emojiCode }
            if (idx == DEFAULT_MESSAGE_ID) {
                this?.emojis?.add(
                    EmojiData(
                        emojiCode = emojiCode,
                        emojiNumber = 1,
                        isCurrUserReacted = true
                    )
                )
            } else {
                this?.emojis?.checkExistedEmoji(
                    idx
                        ?: throw IllegalArgumentException(
                            "idx required"
                        )
                )
            }
        }
        messageAdapter.notifyItemChanged(messageIdx)
        messageIdx = DEFAULT_MESSAGE_ID
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }


    private fun initRecycleView() {
        with(binding.rvMessage) {
            messageAdapter = MessageAdapter(1)
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        with(messageAdapter) {
            initListener(this@MainActivity)
            submitList(listRecyclerView)
        }
    }

    override fun getBottomSheet(messageId: Int): Boolean {
        messageIdx = messageId
        bottomSheetDialog.show()
        return true
    }

    override fun onEmojiClick(emojiCode: String, idx: Int): Boolean {
        messageIdx = idx
        onEmojiClicked(emojiCode)
        return true
    }

    companion object {
        const val DEFAULT_MESSAGE_ID = -1
        const val CURR_USER_ID = 1
        const val CURR_USER_NAME = "Марк Цукерберг"
        const val CURR_USER_AVATAR_URL = "https://clck.ru/YDyYU"
        const val CURR_USER_DATE = "3 фев"
    }
}
