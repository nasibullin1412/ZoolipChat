package com.homework.coursework.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.homework.coursework.R
import com.homework.coursework.adapters.MessageAdapter
import com.homework.coursework.customview.CustomFlexboxLayout
import com.homework.coursework.data.BaseItem
import com.homework.coursework.data.EmojiData
import com.homework.coursework.data.MessageData
import com.homework.coursework.data.UserData
import com.homework.coursework.databinding.TopicDiscussionFragmentBinding
import com.homework.coursework.interfaces.MessageItemCallback
import com.homework.coursework.testList
import com.homework.coursework.utils.addDate
import com.homework.coursework.utils.addMessageData
import com.homework.coursework.utils.checkExistedEmoji
import com.homework.coursework.utils.initEmojiToBottomSheet

class TopicDiscussionFragment: Fragment(), MessageItemCallback {

    private var _binding: TopicDiscussionFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private var listRecyclerView: ArrayList<BaseItem> = arrayListOf()
    private var messageIdx = DEFAULT_MESSAGE_ID


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TopicDiscussionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            context?.theme
        )
        binding.etMessage.apply {
            addTextChangedListener {
                binding.imgSend.background = ResourcesCompat.getDrawable(
                    resources,
                    selectIcon(text.toString()),
                    context.theme
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
        bottomSheetDialog = BottomSheetDialog(
            context ?: throw IllegalArgumentException("Context required")
        ).apply {
            setContentView(R.layout.bottom_sheet)
            findViewById<CardView>(R.id.cvEmojiBottom)?.apply {
                background = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.sh_bottom_sheet,
                    context.theme
                )
            }
        }
        val emojiCodes = resources.getStringArray(R.array.emoji_codes)
        val flbLayout = bottomSheetDialog.findViewById<CustomFlexboxLayout>(R.id.fblBottomSheet)
        for (emoji in emojiCodes) {
            flbLayout?.addView(
                TextView(context).apply {
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
            layoutManager = LinearLayoutManager(context)
        }
        with(messageAdapter) {
            initListener(this@TopicDiscussionFragment)
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

