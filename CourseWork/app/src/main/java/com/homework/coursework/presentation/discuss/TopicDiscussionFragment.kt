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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.homework.coursework.R
import com.homework.coursework.databinding.TopicDiscussionFragmentBinding
import com.homework.coursework.presentation.adapter.MessageAdapter
import com.homework.coursework.presentation.adapter.data.DiscussItem
import com.homework.coursework.presentation.adapter.data.EmojiItem
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.customview.CustomFlexboxLayout
import com.homework.coursework.presentation.interfaces.BottomNavigationController
import com.homework.coursework.presentation.interfaces.MessageItemCallback
import com.homework.coursework.presentation.stream.StreamListFragment.Companion.STREAM_KEY
import com.homework.coursework.presentation.stream.StreamListFragment.Companion.TOPIC_KEY
import com.homework.coursework.presentation.utils.Emoji
import com.homework.coursework.presentation.utils.initEmojiToBottomSheet
import com.homework.coursework.presentation.utils.showToast
import okhttp3.internal.toHexString

class TopicDiscussionFragment : Fragment(), MessageItemCallback {

    private var _binding: TopicDiscussionFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private var messageIdx = DEFAULT_MESSAGE_ID
    private var bottomNavigationController: BottomNavigationController? = null
    private lateinit var currentTopic: TopicItem
    private lateinit var currentStream: StreamItem
    private lateinit var scrollListener: PagingScrollListener
    private var currMessageId = 0
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
        viewModel.getMessages(currentStream, currentTopic)
    }

    private fun initObservers() {
        viewModel.topicDiscScreenState.observe(viewLifecycleOwner, { processTopicScreenState(it) })
        binding.errorContent.tvRepeat.setOnClickListener {
            viewModel.getMessages(
                currentStream,
                currentTopic
            )
        }
    }

    private fun processTopicScreenState(stateStream: TopicDiscussionState) {
        when (stateStream) {
            is TopicDiscussionState.Error -> {
                showErrorScreen()
                showToast(stateStream.error.message)
            }
            is TopicDiscussionState.Loading -> {
                showLoadingScreen()
            }
            is TopicDiscussionState.ResultMessages -> {
                showResultScreen()
                createNewRecycleList(stateStream.data)
            }
            TopicDiscussionState.ResultUserChanges -> {
                viewModel.getMessages(
                    currentStream,
                    currentTopic.copy(numberOfMess = currMessageId)
                )
            }
            is TopicDiscussionState.ErrorUserChanges -> {
                showToast(stateStream.error.message)
            }
            is TopicDiscussionState.UpdateRecycleMessages -> {
                updateMessage(stateStream.data)
            }
        }
    }

    /**
     * show error layout
     */
    private fun showErrorScreen() {
        with(binding) {
            rvMessage.visibility = View.GONE
            progressBar.visibility = View.GONE
            nsvErrorConnection.visibility = View.VISIBLE
        }
    }

    /**
     * show loading layout
     */
    private fun showLoadingScreen() {
        with(binding) {
            nsvErrorConnection.visibility = View.GONE
            rvMessage.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    /**
     * show result screen
     */
    private fun showResultScreen() {
        with(binding) {
            nsvErrorConnection.visibility = View.GONE
            progressBar.visibility = View.GONE
            rvMessage.visibility = View.VISIBLE
        }
    }

    private fun createNewRecycleList(newList: List<DiscussItem>) {
        viewModel.doCreateNewRecycleList(
            oldList = messageAdapter.currentList.drop(1),
            newList = newList
        )
    }

    /**
     * update recycler view with new message
     * @param resultList is list with MessageData
     */
    private fun updateMessage(resultList: List<DiscussItem>) {
        messageAdapter.submitList(resultList)
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
        viewModel.doChanges(
            useCaseTypeMessage = UseCaseTypeMessage.ADD_MESSAGE,
            streamItem = currentStream,
            topicItem = currentTopic,
            content = messageContent
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
        bottomSheetDialog = BottomSheetDialog(requireContext()).apply {
            setContentView(R.layout.bottom_sheet)
            findViewById<CardView>(R.id.cvEmojiBottom)?.apply {
                background = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.sh_bottom_sheet,
                    context.theme
                )
            }
        }
        val flbLayout = bottomSheetDialog.findViewById<CustomFlexboxLayout>(R.id.fblBottomSheet)
        for ((idx, emoji) in Emoji.values().withIndex()) {
            flbLayout?.addView(
                TextView(context).apply {
                    initEmojiToBottomSheet(Emoji.toEmoji(emoji.unicodeCodePoint))
                    setOnClickListener { onEmojiClicked(idx) }
                }
            )
        }
    }

    /**
     * In Emoji click callback, which add new emoji or increase and decrease existed
     * emoji number
     * @param emojiIdx is clicked emoji idx
     */
    private fun onEmojiClicked(emojiIdx: Int) {
        if (messageIdx == DEFAULT_MESSAGE_ID) {
            throw IllegalArgumentException("selectedMessageId required")
        }
        with(messageAdapter.currentList[messageIdx].messageItem) {
            val existedEmoji = this?.emojis?.firstOrNull {
                it.emojiCode == Emoji.values()[emojiIdx].unicodeCodePoint.toHexString()
            }
            val emojiForAction = EmojiItem(
                emojiCode = Emoji.values()[emojiIdx].unicodeCodePoint.toHexString(),
                emojiNumber = this?.emojis?.size ?: 0,
                emojiName = Emoji.values()[emojiIdx].nameInZulip,
                isCurrUserReacted = false
            )
            currMessageId = this?.messageId ?: 0
            viewModel.doChanges(
                useCaseTypeReaction = addOrDelete(
                    existedEmoji?.isCurrUserReacted?.not() ?: true
                ),
                messageItem = this ?: throw IllegalArgumentException("message required"),
                emojiItem = emojiForAction
            )
        }
        messageAdapter.notifyItemChanged(messageIdx)
        messageIdx = DEFAULT_MESSAGE_ID
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun onEmojiClicked(emojiItem: EmojiItem) {
        if (messageIdx == DEFAULT_MESSAGE_ID) {
            throw IllegalArgumentException("selectedMessageId required")
        }
        with(messageAdapter.currentList[messageIdx].messageItem) {
            currMessageId = this?.messageId ?: 0
            this?.emojis?.indexOf(emojiItem)
            viewModel.doChanges(
                useCaseTypeReaction = addOrDelete(emojiItem.isCurrUserReacted.not()),
                messageItem = this ?: throw IllegalArgumentException("message required"),
                emojiItem = emojiItem
            )
        }
    }

    private fun addOrDelete(isAdd: Boolean): UseCaseTypeReaction = if (isAdd) {
        UseCaseTypeReaction.ADD_REACTION
    } else {
        UseCaseTypeReaction.DELETE_REACTION
    }

    private fun initRecycleView() {
        with(binding.rvMessage) {
            messageAdapter = MessageAdapter().apply {
                initListener(this@TopicDiscussionFragment)
            }
            adapter = messageAdapter
            val currLayoutManager = LinearLayoutManager(context).apply { stackFromEnd = true }
            layoutManager = currLayoutManager
            scrollListener = object : PagingScrollListener(currLayoutManager) {
                override fun onLoadMore(
                    top: Boolean,
                    totalItemsCount: Int,
                    view: RecyclerView?
                ): Boolean {
                    viewModel.getMessages(
                        stream = currentStream,
                        topic = currentTopic.copy(
                            numberOfMess = messageAdapter.currentList
                                .first { it.messageItem != null }
                                .messageItem?.messageId
                                ?: throw IllegalArgumentException("messageItem requird")),
                    )
                    return true
                }
            }
            addOnScrollListener(scrollListener)
        }
    }

    override fun getBottomSheet(messageId: Int): Boolean {
        messageIdx = messageId
        bottomSheetDialog.show()
        return true
    }

    override fun onEmojiClick(emojiItem: EmojiItem, idx: Int): Boolean {
        messageIdx = idx
        onEmojiClicked(emojiItem)
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomNavigationController?.visibleBottomNavigation()
    }

    override fun onDetach() {
        super.onDetach()
        bottomNavigationController = null
    }

    companion object {
        const val DEFAULT_MESSAGE_ID = -1

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
