package com.homework.coursework.presentation.ui.chat.utils

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.homework.coursework.presentation.adapter.data.EmojiItem
import com.homework.coursework.presentation.adapter.data.chat.MessageItem
import com.homework.coursework.presentation.adapter.data.chat.firstWithMessageItem
import com.homework.coursework.presentation.ui.chat.ChatBaseFragment
import com.homework.coursework.presentation.ui.chat.elm.Event
import com.homework.coursework.presentation.utils.Emoji
import okhttp3.internal.toHexString

/**
 * In Emoji click callback, which add new emoji or increase and decrease existed
 * emoji number
 * @param emojiIdx is clicked emoji idx
 */
internal fun ChatBaseFragment.onEmojiClickedImpl(emojiIdx: Int) {
    if (messageId == ChatBaseFragment.DEFAULT_MESSAGE_ID) {
        throw IllegalArgumentException("selectedMessageId required")
    }
    val message = chatAdapter.currentList.firstWithMessageItem { it == messageId } ?: return
    with(message) {
        val isExistedEmoji = emojis.firstOrNull {
            it.emojiCode == Emoji.values()[emojiIdx].unicodeCodePoint.toHexString()
        }?.isCurrUserReacted?.not() ?: true
        val emojiForAction = EmojiItem(
            emojiCode = Emoji.values()[emojiIdx].unicodeCodePoint.toHexString(),
            emojiNumber = emojis.size,
            emojiName = Emoji.values()[emojiIdx].nameInZulip,
            isCurrUserReacted = false
        )
        updateMessage = messageId
        internalStore.accept(
            event = addOrDelete(
                isAdd = isExistedEmoji,
                messageItem = this,
                emojiItem = emojiForAction
            )
        )
    }
    messageId = ChatBaseFragment.DEFAULT_MESSAGE_ID
    emojiBottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_HIDDEN
}

internal fun ChatBaseFragment.onEmojiClickedImpl(emojiItem: EmojiItem) {
    if (messageId == ChatBaseFragment.DEFAULT_MESSAGE_ID) {
        throw IllegalArgumentException("selectedMessageId required")
    }
    val message = chatAdapter.currentList.firstWithMessageItem { messageId == it } ?: return
    with(message) {
        updateMessage = messageId
        internalStore.accept(
            event = addOrDelete(
                isAdd = emojiItem.isCurrUserReacted.not(),
                messageItem = this,
                emojiItem = emojiItem
            )
        )
    }
    messageId = ChatBaseFragment.DEFAULT_MESSAGE_ID
}

internal fun addOrDelete(isAdd: Boolean, messageItem: MessageItem, emojiItem: EmojiItem) =
    if (isAdd) {
        Event.Ui.AddReaction(messageItem = messageItem, emojiItem = emojiItem)
    } else {
        Event.Ui.DeleteReaction(messageItem = messageItem, emojiItem = emojiItem)
    }
