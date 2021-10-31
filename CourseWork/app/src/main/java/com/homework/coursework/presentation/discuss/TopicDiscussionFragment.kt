package com.homework.coursework.presentation.discuss

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.homework.coursework.R
import com.homework.coursework.databinding.TopicDiscussionFragmentBinding
import com.homework.coursework.domain.entity.EmojiData
import com.homework.coursework.presentation.MainActivity.Companion.CURR_USER_DATE
import com.homework.coursework.presentation.MainActivity.Companion.DEFAULT_MESSAGE_ID
import com.homework.coursework.presentation.adapter.MessageAdapter
import com.homework.coursework.presentation.adapter.data.MessageItem
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.customview.CustomFlexboxLayout
import com.homework.coursework.presentation.interfaces.BottomNavigationController
import com.homework.coursework.presentation.interfaces.MessageItemCallback
import com.homework.coursework.presentation.stream.StreamListFragment.Companion.STREAM_KEY
import com.homework.coursework.presentation.stream.StreamListFragment.Companion.TOPIC_KEY
import com.homework.coursework.presentation.utils.checkExistedEmoji
import com.homework.coursework.presentation.utils.initEmojiToBottomSheet

class TopicDiscussionFragment : Fragment(), MessageItemCallback {

    private var _binding: TopicDiscussionFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private var messageIdx = DEFAULT_MESSAGE_ID
    private var bottomNavigationController: BottomNavigationController? = null
    private lateinit var currentTopic: TopicItem
    private lateinit var currentStream: StreamItem
    private val viewModel: TopicDiscussionViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomNavigationController) {
            bottomNavigationController = context
        }
    }

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
        bottomNavigationController?.goneBottomNavigation()
        initViews()
    }

    private fun initViews() {
        initRecycleView()
        initObservers()
        initBottomDialog()
        initButtonListener()
        initEditText()
        initNames()
        initBackButton()
        viewModel.getMessages(currentStream.id, currentStream.id)
    }

    private fun initObservers() {
        viewModel.topicDiscScreenState.observe(viewLifecycleOwner, { processTopicScreenState(it) })
    }

    private fun processTopicScreenState(stateStream: TopicDiscussionState) {
        when (stateStream) {
            is TopicDiscussionState.Error -> {

            }
            is TopicDiscussionState.Loading -> {

            }
            is TopicDiscussionState.Result -> {
                updateMessage(stateStream.data)
            }
        }
    }

    /**
     * update recycler view with new message
     * @param newList is list with new MessageData
     */
    private fun updateMessage(newList: List<MessageItem>) {
        messageAdapter.submitList(newList)
    }

    private fun initBackButton() {
        binding.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initNames() {
        currentTopic = requireArguments().getParcelable(TOPIC_KEY)
            ?: throw IllegalArgumentException("topic required")
        currentStream = requireArguments().getParcelable(STREAM_KEY)
            ?: throw IllegalArgumentException("stream required")
        binding.tvStreamNameBar.text = currentStream.streamName
        binding.tvTopicName.text = "Topic: ${currentTopic.topicName}"
    }

    private fun initButtonListener() {
        binding.imgSend.setOnClickListener {
            updateMessageRecycler(binding.etMessage.text.toString())
            binding.etMessage.setText("")
        }
    }

    private fun updateMessageRecycler(messageContent: String) {
        viewModel.addMessage(
            currentStream.id,
            currentTopic.id,
            messageContent,
            messageAdapter.currentList.size,
            CURR_USER_DATE
        )
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
        with(messageAdapter.currentList[messageIdx].messageData) {
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
            messageAdapter = MessageAdapter(1).apply {
                initListener(this@TopicDiscussionFragment)
            }
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(context).apply {
                stackFromEnd = true
            }
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

    override fun onDetach() {
        super.onDetach()
        bottomNavigationController = null
    }

    companion object {
        fun newInstance(topic: TopicItem, stream: StreamItem): TopicDiscussionFragment {
            val args = Bundle()
            args.putParcelable(TOPIC_KEY, topic)
            args.putParcelable(STREAM_KEY, stream)
            val fragment = TopicDiscussionFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
