package com.homework.coursework.presentation.discuss

import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.homework.coursework.R
import com.homework.coursework.presentation.adapter.MessageAdapter
import com.homework.coursework.presentation.adapter.data.DiscussItem
import com.homework.coursework.presentation.adapter.data.EmojiItem
import com.homework.coursework.presentation.adapter.data.MessageItem
import com.homework.coursework.presentation.customview.CustomFlexboxLayout
import com.homework.coursework.presentation.discuss.ChatBaseFragment.Companion.DEFAULT_MESSAGE_ID
import com.homework.coursework.presentation.discuss.elm.Event
import com.homework.coursework.presentation.discuss.main.TopicChatFragment
import com.homework.coursework.presentation.utils.Emoji
import com.homework.coursework.presentation.utils.initEmojiToBottomSheet
import com.homework.coursework.presentation.utils.toStringDate
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.internal.toHexString
import java.util.*

@ExperimentalSerializationApi
internal fun TopicChatFragment.initRecycleViewImpl() {
    with(binding.rvMessage) {
        messageAdapter = MessageAdapter().apply {
            initListener(this@initRecycleViewImpl)
        }
        adapter = messageAdapter
        val currLayoutManager = LinearLayoutManager(context).apply { stackFromEnd = true }
        layoutManager = currLayoutManager
        scrollListener = object : PagingScrollListener(currLayoutManager) {
            override fun onLoadMore(top: Boolean): Boolean {
                internalStore.accept(
                    Event.Ui.LoadNextPage(
                        streamItem = currentStream,
                        topicItem = currentTopic.copy(
                            numberOfMess = messageAdapter.currentList
                                .first { it.messageItem != null }
                                .messageItem?.messageId
                                ?: throw IllegalArgumentException("messageItem required"))
                    )
                )
                return true
            }
        }
        addOnScrollListener(scrollListener)
    }
}

internal fun ChatBaseFragment.initBottomDialog() {
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
@ExperimentalSerializationApi
internal fun TopicChatFragment.onEmojiClickedImpl(emojiIdx: Int) {
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
        internalStore.accept(
            event = addOrDelete(
                isAdd = existedEmoji?.isCurrUserReacted?.not() ?: true,
                messageItem = this ?: throw IllegalArgumentException("message required"),
                emojiItem = emojiForAction
            )
        )
    }
    messageAdapter.notifyItemChanged(messageIdx)
    messageIdx = DEFAULT_MESSAGE_ID
    bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_HIDDEN
}

@ExperimentalSerializationApi
internal fun TopicChatFragment.onEmojiClickedImpl(emojiItem: EmojiItem) {
    if (messageIdx == DEFAULT_MESSAGE_ID) {
        throw IllegalArgumentException("selectedMessageId required")
    }
    with(messageAdapter.currentList[messageIdx].messageItem) {
        currMessageId = this?.messageId ?: 0
        this?.emojis?.indexOf(emojiItem)
        internalStore.accept(
            event = addOrDelete(
                isAdd = emojiItem.isCurrUserReacted.not(),
                messageItem = this ?: throw IllegalArgumentException("message required"),
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

/**
 * add date to recycler list
 * @param date is string with date
 */
fun ArrayList<DiscussItem>.addDate(date: String) {
    if (lastIndex == -1 || this[lastIndex].messageItem?.date?.toStringDate() == date) {
        return
    }
    val curIdx = lastIndex + 1
    add(
        DiscussItem(
            id = curIdx,
            messageItem = null,
            date = date
        )
    )
}

/**
 * add message to recycler list
 * @param messageItemList is list with new messages
 */
fun ArrayList<DiscussItem>.addMessageItem(messageItemList: List<MessageItem>) {
    val idx = lastIndex + 1
    addAll(
        messageItemList.mapIndexed { index, messageItem ->
            DiscussItem(
                id = idx + index,
                messageItem = messageItem,
                date = null
            )
        }
    )
}
