package com.homework.coursework.presentation.viewholder.chat

import coil.load
import coil.transform.CircleCropTransformation
import com.homework.coursework.R
import com.homework.coursework.databinding.CustomMessageViewGroupBinding
import com.homework.coursework.databinding.ItemMessageFromBinding
import com.homework.coursework.presentation.adapter.data.chat.MessageItem
import com.homework.coursework.presentation.interfaces.MessageItemCallback
import com.homework.coursework.presentation.utils.NUMBER_OF_HYPHENATIONS
import com.homework.coursework.presentation.utils.emojiLogic

class MessageFromViewHolder(
    private val listener: MessageItemCallback,
    viewBinding: ItemMessageFromBinding
) : ChatViewHolder(viewBinding.root) {

    private val customMessageViewGroupBinding = CustomMessageViewGroupBinding.bind(viewBinding.root)

    fun bind(messageItem: MessageItem) {
        with(customMessageViewGroupBinding) {
            imgUserAvatar
                .load(messageItem.userItem.avatarUrl) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            cvMessageFrom.setBackgroundResource(R.drawable.bg_custom_message)
            tvUserName.text = messageItem.userItem.name
            tvMessageContent.text = messageItem.messageContent.dropLast(NUMBER_OF_HYPHENATIONS)
            with(fblEmoji) {
                removeAllViews()
                emojiLogic(
                    messageItem = messageItem,
                    idx = messageItem.messageId,
                    listener = listener
                )
            }
        }
    }
}
