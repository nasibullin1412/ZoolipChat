package com.homework.coursework.presentation.viewholder.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import com.homework.coursework.databinding.DateItemBinding
import com.homework.coursework.databinding.MessageFromItemBinding
import com.homework.coursework.databinding.MessageToItemBinding
import com.homework.coursework.databinding.TopicChatItemBinding
import com.homework.coursework.presentation.interfaces.MessageItemCallback

class ChatViewHolderFactory(var viewHolder: ChatViewHolder) {
    companion object {
        fun create(
            viewType: Int,
            listener: MessageItemCallback,
            parent: ViewGroup
        ): ChatViewHolder {
            val inflater: LayoutInflater = LayoutInflater.from(parent.context)
            return when (ChatViewType.getChatViewTypeByInt(viewType)) {
                ChatViewType.MESSAGE_TO -> MessageToViewHolder(
                    listener,
                    MessageToItemBinding.inflate(inflater, parent, false)
                )
                ChatViewType.MESSAGE_FROM -> MessageFromViewHolder(
                    listener,
                    MessageFromItemBinding.inflate(inflater, parent, false)
                )
                ChatViewType.DATE -> DateViewHolder(
                    DateItemBinding.inflate(inflater, parent, false)
                )
                ChatViewType.TOPIC_NAME -> TopicNameViewHolder(
                    TopicChatItemBinding.inflate(inflater, parent, false)
                )
            }
        }
    }
}
