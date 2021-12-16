package com.homework.coursework.presentation.ui.chat

import android.view.View
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.homework.coursework.R
import com.homework.coursework.databinding.BottomSheetActionBinding
import com.homework.coursework.databinding.BottomSheetBinding
import com.homework.coursework.presentation.adapter.ChatAdapter
import com.homework.coursework.presentation.adapter.data.EmojiItem
import com.homework.coursework.presentation.adapter.data.chat.MessageItem
import com.homework.coursework.presentation.adapter.data.chat.firstWithMessageItem
import com.homework.coursework.presentation.ui.chat.ChatBaseFragment.Companion.DEFAULT_MESSAGE_ID
import com.homework.coursework.presentation.ui.chat.elm.Event
import com.homework.coursework.presentation.utils.Emoji
import com.homework.coursework.presentation.utils.initEmojiToBottomSheet
import okhttp3.internal.toHexString
import java.util.*

internal fun ChatBaseFragment.initRecycleViewImpl() {
    with(binding.rvMessage) {
        chatAdapter = ChatAdapter().apply { initMessageListener(this@initRecycleViewImpl) }
        adapter = chatAdapter
        val currLayoutManager = LinearLayoutManager(context).apply { stackFromEnd = true }
        layoutManager = currLayoutManager
        scrollListener = object : PagingScrollListener(currLayoutManager) {
            override fun onLoadMore(top: Boolean): Boolean {
                val firstMessageItem = chatAdapter.currentList.first { it is MessageItem }
                if (firstMessageItem is MessageItem) {
                    internalStore.accept(
                        Event.Ui.LoadNextPage(
                            streamItem = currentStream,
                            topicItem = currentTopic.copy(
                                numberOfMess = firstMessageItem.messageId
                            ),
                            currId = currId
                        )
                    )
                }
                return true
            }
        }
        addOnScrollListener(scrollListener)
    }
}

internal fun ChatBaseFragment.showMessageActions(message: MessageItem) {
    val bottomSheetDialog = BottomSheetDialog(requireContext())
    val sheetBinding = BottomSheetActionBinding.inflate(layoutInflater).apply {
        tvEditMessage.visibility = isViewVisible(message.isCurrentUserMessage)
        tvDeleteMessage.visibility = isViewVisible(message.isCurrentUserMessage)
    }
    sheetBinding.tvAddReaction.setOnClickListener {
        bottomSheetDialog.dismiss()
        showReactionBottomSheet(message)
    }
    sheetBinding.tvEditMessage.setOnClickListener {
        bottomSheetDialog.dismiss()
        //editMessage(message)
    }
    sheetBinding.tvCopyToClipboard.setOnClickListener {
        bottomSheetDialog.dismiss()
        //copyToClipboard(message)
    }
    sheetBinding.tvDeleteMessage.setOnClickListener {
        bottomSheetDialog.dismiss()
        //deleteMessage(message)
    }
    with(bottomSheetDialog){
        setContentView(sheetBinding.root)
        show()
    }
}

internal fun ChatBaseFragment.showReactionBottomSheet(message: MessageItem) {
    messageId = message.messageId
    emojiBottomSheetDialog.show()
}

private fun isViewVisible(condition: Boolean) = if (condition) View.VISIBLE else View.GONE

internal fun ChatBaseFragment.initEmojiBottomDialog() {
    emojiBottomSheetDialog = BottomSheetDialog(requireContext())
    val sheetBinding = BottomSheetBinding.inflate(layoutInflater)
    sheetBinding.cvEmojiBottom.background = ResourcesCompat.getDrawable(
        resources,
        R.drawable.sh_bottom_sheet,
        requireContext().theme
    )
    with(sheetBinding.fblBottomSheet) {
        for ((idx, emoji) in Emoji.values().withIndex()) {
            addView(
                TextView(context).apply {
                    initEmojiToBottomSheet(Emoji.toEmoji(emoji.unicodeCodePoint))
                    setOnClickListener { onEmojiClickedImpl(idx) }
                }
            )
        }
    }
    emojiBottomSheetDialog.setContentView(sheetBinding.root)
}

/**
 * In Emoji click callback, which add new emoji or increase and decrease existed
 * emoji number
 * @param emojiIdx is clicked emoji idx
 */
internal fun ChatBaseFragment.onEmojiClickedImpl(emojiIdx: Int) {
    if (messageId == DEFAULT_MESSAGE_ID) {
        throw IllegalArgumentException("selectedMessageId required")
    }
    val chatItem = chatAdapter.currentList.firstWithMessageItem { it == messageId }
    with(chatItem) {
        val existedEmoji = emojis.firstOrNull {
            it.emojiCode == Emoji.values()[emojiIdx].unicodeCodePoint.toHexString()
        }
        val emojiForAction = EmojiItem(
            emojiCode = Emoji.values()[emojiIdx].unicodeCodePoint.toHexString(),
            emojiNumber = emojis.size,
            emojiName = Emoji.values()[emojiIdx].nameInZulip,
            isCurrUserReacted = false
        )
        updateMessage = messageId
        internalStore.accept(
            event = addOrDelete(
                isAdd = existedEmoji?.isCurrUserReacted?.not() ?: true,
                messageItem = this,
                emojiItem = emojiForAction
            )
        )
    }
    messageId = DEFAULT_MESSAGE_ID
    emojiBottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_HIDDEN
}

internal fun ChatBaseFragment.onEmojiClickedImpl(emojiItem: EmojiItem) {
    if (messageId == DEFAULT_MESSAGE_ID) {
        throw IllegalArgumentException("selectedMessageId required")
    }
    val chatItem = chatAdapter.currentList.firstWithMessageItem { messageId == it }
    with(chatItem) {
        updateMessage = messageId
        internalStore.accept(
            event = addOrDelete(
                isAdd = emojiItem.isCurrUserReacted.not(),
                messageItem = this,
                emojiItem = emojiItem
            )
        )
    }
}

internal fun addOrDelete(isAdd: Boolean, messageItem: MessageItem, emojiItem: EmojiItem) =
    if (isAdd) {
        Event.Ui.AddReaction(messageItem = messageItem, emojiItem = emojiItem)
    } else {
        Event.Ui.DeleteReaction(messageItem = messageItem, emojiItem = emojiItem)
    }

internal fun ChatBaseFragment.initEditText() {
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

internal fun selectIcon(text: String): Int = if (text.isEmpty()) {
    R.drawable.ic_vector
} else {
    R.drawable.ic_vector_send
}
