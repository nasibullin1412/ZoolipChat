package com.homework.coursework.presentation.ui.chat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResultListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.homework.coursework.R
import com.homework.coursework.databinding.FragmentChatBinding
import com.homework.coursework.presentation.adapter.ChatAdapter
import com.homework.coursework.presentation.adapter.data.EmojiItem
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.adapter.data.chat.ChatItem
import com.homework.coursework.presentation.adapter.data.chat.MessageItem
import com.homework.coursework.presentation.interfaces.BottomNavigationController
import com.homework.coursework.presentation.interfaces.MessageItemCallback
import com.homework.coursework.presentation.interfaces.NavigateController
import com.homework.coursework.presentation.ui.chat.elm.Effect
import com.homework.coursework.presentation.ui.chat.elm.Event
import com.homework.coursework.presentation.ui.chat.elm.State
import com.homework.coursework.presentation.ui.chat.utils.*
import com.homework.coursework.presentation.ui.editmessage.main.EditMessageFragment.Companion.EDIT_MESSAGE_REQUEST_KEY
import com.homework.coursework.presentation.utils.showToast
import vivid.money.elmslie.android.base.ElmFragment
import java.io.FileNotFoundException

abstract class ChatBaseFragment : ElmFragment<Event, Effect, State>(), MessageItemCallback {

    internal lateinit var chatAdapter: ChatAdapter

    internal lateinit var scrollListener: PagingScrollListener

    internal lateinit var emojiBottomSheetDialog: BottomSheetDialog

    internal var messageId = DEFAULT_MESSAGE_ID

    internal var currId: Int = 0

    internal var updateMessage: Int = 0

    internal val internalStore get() = store

    internal val binding get() = _binding!!

    internal lateinit var currentTopic: TopicItem

    internal lateinit var currentStream: StreamItem

    protected var bottomNavigationController: BottomNavigationController? = null

    internal var navigateController: NavigateController? = null

    private var _binding: FragmentChatBinding? = null

    private val startForResultSendFile = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            addFileAction(result.data?.data)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomNavigationController) {
            bottomNavigationController = context
        }
        if (context is NavigateController) {
            navigateController = context
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
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationController?.goneBottomNavigation()
        initViews()
        initFragmentResult()
    }

    override fun getBottomSheet(messageItem: MessageItem): Boolean {
        showMessageActions(messageItem)
        return true
    }

    override fun onEmojiClick(emojiItem: EmojiItem, messageId: Int) {
        this.messageId = messageId
        onEmojiClickedImpl(emojiItem)
    }

    override fun onDetach() {
        super.onDetach()
        bottomNavigationController = null
        navigateController = null
    }

    override val initEvent: Event get() = Event.Ui.GetCurrentId

    abstract fun initArguments()

    abstract fun sendButtonAction()

    abstract fun initNames()

    abstract fun configureView()

    abstract fun getTopicName(): String

    abstract fun updateMessage(newList: List<ChatItem>)

    abstract fun initMessage(newList: List<ChatItem>)

    private fun initViews() {
        initRecycleViewImpl()
        configureView()
        initErrorRepeat()
        initEmojiBottomDialog()
        initButtonListener()
        initEditText()
        initNames()
        initBackButton()
    }

    private fun initFragmentResult() {
        setFragmentResultListener(EDIT_MESSAGE_REQUEST_KEY) { _, _ ->
            store.accept(
                Event.Ui.LoadFirstPage(
                    streamItem = currentStream,
                    topicItem = currentTopic,
                    currId = currId
                )
            )
        }
    }

    private fun initButtonListener() {
        binding.imgSend.setOnClickListener {
            if (binding.etMessage.text.isEmpty()) {
                showFileChooser()
            } else {
                sendButtonAction()
            }
        }
    }


    private fun showFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startForResultSendFile.launch(
            Intent.createChooser(
                intent,
                getString(R.string.title_select_file)
            )
        )
    }

    private fun addFileAction(uri: Uri?) {
        if (uri == null) return
        val topicName = getTopicName()
        if (topicName.isEmpty()) return
        with(requireActivity().contentResolver) {
            try {
                val input = openInputStream(uri) ?: return
                store.accept(
                    Event.Ui.AddFile(
                        streamItem = currentStream,
                        topicItem = currentTopic.copy(topicName = topicName),
                        input = input,
                        fileName = getFileName(uri)
                    )
                )
            } catch (ex: FileNotFoundException) {
                showToast(ex.message)
            }
        }
    }

    private fun getFileName(uri: Uri): String {
        return uri.lastPathSegment ?: DEFAULT_FILE_NAME
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
        if (state.isFirstLoad) {
            showResultScreen()
            initMessage(state.itemList)
        }
    }

    override fun handleEffect(effect: Effect) {
        when (effect) {
            is Effect.ErrorCommands -> showToast(effect.error.message)
            is Effect.NextPageLoadError -> showToast(effect.error.message)
            is Effect.MessageAdded -> messageAddedAction(effect.id)
            is Effect.SuccessGetId -> successGetIdAction(effect.currId)
            Effect.MessagesSaved -> Log.d("SaveLog", "Success save all messages")
            is Effect.PageLoaded -> pageLoadedAction(effect.itemList)
            Effect.ReactionChanged -> reactionChangedAction()
            Effect.DeleteMessage -> deleteMessageAction()
        }
    }

    private fun messageAddedAction(id: Int) {
        chatAdapter.submitList(emptyList())
        store.accept(
            Event.Ui.LoadNextPage(
                streamItem = currentStream,
                topicItem = currentTopic.copy(numberOfMess = id),
                currId = currId
            )
        )
    }

    private fun successGetIdAction(currIdValue: Int) {
        currId = currIdValue
        store.accept(
            Event.Ui.LoadFirstPage(
                streamItem = currentStream,
                topicItem = currentTopic,
                currId = currIdValue
            )
        )
    }

    private fun pageLoadedAction(chatList: List<ChatItem>) {
        store.accept(
            Event.Ui.MergeOldList(
                oldList = chatAdapter.currentList,
                newList = chatList
            )
        )
    }

    private fun reactionChangedAction() {
        store.accept(
            Event.Ui.UpdateMessage(
                streamItem = currentStream,
                topicItem = currentTopic.copy(numberOfMess = updateMessage),
                currId = currId
            )
        )
    }

    private fun deleteMessageAction() {
        store.accept(
            Event.Ui.DeleteMessageFromRecycle(chatAdapter.currentList, updateMessage)
        )
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
        const val MESSAGE_CONTENT = "messageContent"
        const val DEFAULT_FILE_NAME = "not_found"
    }
}
