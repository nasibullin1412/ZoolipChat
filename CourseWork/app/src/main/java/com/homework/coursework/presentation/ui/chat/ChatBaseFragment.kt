package com.homework.coursework.presentation.ui.chat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.homework.coursework.databinding.ChatFragmentBinding
import com.homework.coursework.presentation.adapter.MessageAdapter
import com.homework.coursework.presentation.adapter.data.EmojiItem
import com.homework.coursework.presentation.ui.chat.elm.Effect
import com.homework.coursework.presentation.ui.chat.elm.Event
import com.homework.coursework.presentation.ui.chat.elm.State
import com.homework.coursework.presentation.interfaces.BottomNavigationController
import com.homework.coursework.presentation.interfaces.MessageItemCallback
import vivid.money.elmslie.android.base.ElmFragment

abstract class ChatBaseFragment : ElmFragment<Event, Effect, State>(), MessageItemCallback {

    internal lateinit var messageAdapter: MessageAdapter

    internal lateinit var scrollListener: PagingScrollListener

    internal lateinit var bottomSheetDialog: BottomSheetDialog

    internal var messageIdx = DEFAULT_MESSAGE_ID

    internal var currId: Int = 0

    internal var currMessageId = 0

    internal val internalStore get() = store

    internal val binding get() = _binding!!

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
        bottomNavigationController?.visibleBottomNavigation()
        initViews()
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

    override val initEvent: Event
        get() = Event.Ui.GetCurrentId

    abstract fun initErrorRepeat()

    abstract fun onEmojiClicked(emojiIdx: Int)

    abstract fun onEmojiClicked(emojiItem: EmojiItem)

    abstract fun initRecycleView()

    abstract fun initArguments()

    abstract fun initButtonListener()

    abstract fun initNames()

    /**
     * show error layout
     */
    protected fun showErrorScreen() {
        with(binding) {
            rvMessage.visibility = View.GONE
            progressBar.visibility = View.GONE
            nsvErrorConnection.visibility = View.VISIBLE
        }
    }

    /**
     * show loading layout
     */
    protected fun showLoadingScreen() {
        with(binding) {
            nsvErrorConnection.visibility = View.GONE
            rvMessage.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    /**
     * show result screen
     */
    protected fun showResultScreen() {
        with(binding) {
            nsvErrorConnection.visibility = View.GONE
            progressBar.visibility = View.GONE
            rvMessage.visibility = View.VISIBLE
        }
    }

    private fun initViews() {
        initRecycleView()
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
    }
}
