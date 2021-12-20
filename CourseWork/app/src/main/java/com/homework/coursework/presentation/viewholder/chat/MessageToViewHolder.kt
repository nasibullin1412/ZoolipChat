package com.homework.coursework.presentation.viewholder.chat

import com.homework.coursework.R
import com.homework.coursework.databinding.MessageToItemBinding
import com.homework.coursework.presentation.adapter.data.chat.MessageItem
import com.homework.coursework.presentation.interfaces.MessageItemCallback
import com.homework.coursework.presentation.utils.NUMBER_OF_HYPHENATIONS
import com.homework.coursework.presentation.utils.emojiLogic

class MessageToViewHolder(
    private val listener: MessageItemCallback,
    private val viewBinding: MessageToItemBinding
) : ChatViewHolder(viewBinding.root) {

    fun bind(messageItem: MessageItem) {
        with(viewBinding) {
            tvMessageContentTo.text = messageItem.messageContent.dropLast(NUMBER_OF_HYPHENATIONS)
            cvMessageTo.setBackgroundResource(R.drawable.bg_custom_message)
            with(fblEmoji){
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
