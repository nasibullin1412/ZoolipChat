package com.homework.coursework.presentation.discuss

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.homework.coursework.databinding.ChatFragmentBinding
import com.homework.coursework.presentation.adapter.MessageAdapter
import com.homework.coursework.presentation.adapter.data.EmojiItem
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.discuss.elm.Effect
import com.homework.coursework.presentation.discuss.elm.Event
import com.homework.coursework.presentation.discuss.elm.State
import com.homework.coursework.presentation.interfaces.BottomNavigationController
import com.homework.coursework.presentation.interfaces.MessageItemCallback
import com.homework.coursework.presentation.stream.StreamItemBaseFragment
import vivid.money.elmslie.android.base.ElmFragment

abstract class ChatBaseFragment : ElmFragment<Event, Effect, State>(), MessageItemCallback {

    private var bottomNavigationController: BottomNavigationController? = null

    internal lateinit var messageAdapter: MessageAdapter

    internal lateinit var scrollListener: PagingScrollListener

    internal lateinit var bottomSheetDialog: BottomSheetDialog

    internal var messageIdx = DEFAULT_MESSAGE_ID

    internal var currMessageId = 0

    internal val internalStore get() = store

    private var _binding: ChatFragmentBinding? = null

    internal val binding get() = _binding!!

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
            activity?.onBackPressed()
        }
    }

    companion object {
        const val DEFAULT_MESSAGE_ID = -1
        const val DATABASE_MESSAGE_THRESHOLD = 50
    }

}