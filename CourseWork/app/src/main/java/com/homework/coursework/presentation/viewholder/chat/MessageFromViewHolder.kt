package com.homework.coursework.presentation.viewholder.chat

import coil.load
import coil.transform.CircleCropTransformation
import com.homework.coursework.R
import com.homework.coursework.databinding.CustomMessageViewGroupBinding
import com.homework.coursework.databinding.MessageFromItemBinding
import com.homework.coursework.presentation.adapter.data.MessageItem
import com.homework.coursework.presentation.interfaces.MessageItemCallback
import com.homework.coursework.presentation.utils.emojiLogic

class MessageFromViewHolder(
    private val listener: MessageItemCallback,
    viewBinding: MessageFromItemBinding
) : ChatViewHolder(viewBinding.root) {

    private val customMessageViewGroupBinding = CustomMessageViewGroupBinding.bind(viewBinding.root)

    fun bind(messageItem: MessageItem) {
        with(customMessageViewGroupBinding) {
            imgUserAvatar
                .load(messageItem.userData.avatarUrl) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            cvMessageFrom.setBackgroundResource(R.drawable.bg_custom_message)
            tvUserName.text = messageItem.userData.name
            tvMessageContent.text = messageItem.messageContent.dropLast(2)
            fblEmoji.emojiLogic(
                messageItem = messageItem,
                idx = messageItem.messageId,
                listener = listener
            )
        }
    }
}
