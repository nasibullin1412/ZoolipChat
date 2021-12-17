package com.homework.coursework.presentation.ui.chat.utils

import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.homework.coursework.R
import com.homework.coursework.databinding.BottomSheetActionBinding
import com.homework.coursework.databinding.BottomSheetBinding
import com.homework.coursework.presentation.adapter.data.chat.MessageItem
import com.homework.coursework.presentation.ui.chat.ChatBaseFragment
import com.homework.coursework.presentation.utils.Emoji
import com.homework.coursework.presentation.utils.initEmojiToBottomSheet

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
    with(bottomSheetDialog) {
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
