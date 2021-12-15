package com.homework.coursework.presentation.ui.chat

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.homework.coursework.databinding.ChatFragmentBinding
import com.homework.coursework.presentation.adapter.ChatAdapter
import com.homework.coursework.presentation.adapter.data.chat.ChatItem
import com.homework.coursework.presentation.adapter.data.EmojiItem
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.interfaces.BottomNavigationController
import com.homework.coursework.presentation.interfaces.MessageItemCallback
import com.homework.coursework.presentation.ui.chat.elm.Effect
import com.homework.coursework.presentation.ui.chat.elm.Event
import com.homework.coursework.presentation.ui.chat.elm.State
import com.homework.coursework.presentation.utils.showToast
import vivid.money.elmslie.android.base.ElmFragment

abstract class ChatBaseFragment : ElmFragment<Event, Effect, State>(), MessageItemCallback {

    internal lateinit var chatAdapter: ChatAdapter

    internal lateinit var scrollListener: PagingScrollListener

    internal lateinit var bottomSheetDialog: BottomSheetDialog

    internal var messageId = DEFAULT_MESSAGE_ID

    internal var currId: Int = 0

    internal var updateMessage: Int = 0

    internal val internalStore get() = store

    internal val binding get() = _binding!!

    internal lateinit var currentTopic: TopicItem

    internal lateinit var currentStream: StreamItem

    protected var bottomNavigationController: BottomNavigationController? = null

    private var _binding: ChatFragmentBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomNavigationController) {
            bottomNavigationController = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChatFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationController?.goneBottomNavigation()
        initViews()
    }

    override fun getBottomSheet(messageId: Int): Boolean {
        this.messageId = messageId
        bottomSheetDialog.show()
        return true
    }

    override fun onEmojiClick(emojiItem: EmojiItem, messageId: Int) {
        this.messageId = messageId
        onEmojiClickedImpl(emojiItem)
    }

    override fun onDetach() {
        super.onDetach()
        bottomNavigationController = null
    }

    override val initEvent: Event get() = Event.Ui.GetCurrentId

    abstract fun initArguments()

    abstract fun sendButtonAction()

    abstract fun initNames()

    abstract fun configureView()

    abstract fun updateMessage(newList: List<ChatItem>)

    private fun initButtonListener() {
        binding.imgSend.setOnClickListener {
            sendButtonAction()
        }
    }

    private fun initErrorRepeat() {
        binding.errorContent.tvRepeat.setOnClickListener {
            store.accept(
                Event.Ui.LoadFirstPage(
                    streamItem = currentStream,
                    topicItem = currentTopic,
                    currId = currId
                )
            )
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

    override fun render(state: State) {
        if (state.isLoading) {
            showLoadingScreen()
            return
        }
        if (state.isError) {
            showErrorScreen()
            return
        }
        if (state.isUpdate) {
            showResultScreen()
            updateMessage(state.itemList)
        }
    }

    override fun handleEffect(effect: Effect) {
        when (effect) {
            is Effect.ErrorCommands -> {
                showToast(effect.error.message)
            }
            is Effect.MessageAdded -> {
                chatAdapter.submitList(emptyList())
                store.accept(
                    Event.Ui.LoadNextPage(
                        streamItem = currentStream,
                        topicItem = currentTopic.copy(numberOfMess = effect.id),
                        currId = currId
                    )
                )
            }
            Effect.MessagesSaved -> {
                Log.d("SaveLog", "Success save all messages")
            }
            is Effect.NextPageLoadError -> {
                showToast(effect.error.message)
            }
            is Effect.PageLoaded -> {
                store.accept(
                    Event.Ui.MergeOldList(
                        oldList = chatAdapter.currentList,
                        newList = effect.itemList
                    )
                )
            }
            Effect.ReactionChanged -> {
                store.accept(
                    Event.Ui.UpdateMessage(
                        streamItem = currentStream,
                        topicItem = currentTopic.copy(numberOfMess = updateMessage),
                        currId = currId
                    )
                )
            }
            is Effect.SuccessGetId -> {
                currId = effect.currId
                store.accept(
                    Event.Ui.LoadFirstPage(
                        streamItem = currentStream,
                        topicItem = currentTopic,
                        currId = currId
                    )
                )
            }
        }
    }

    private fun initViews() {
        initRecycleViewImpl()
        configureView()
        initErrorRepeat()
        initBottomDialog()
        initButtonListener()
        initEditText()
        initNames()
        initBackButton()
    }

    private fun initBackButton() {
        binding.imgBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    companion object {
        const val DEFAULT_MESSAGE_ID = -1
        const val DATABASE_MESSAGE_THRESHOLD = 50
        const val STREAM_KEY = "stream"
    }
}
