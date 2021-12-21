package com.homework.coursework.presentation.viewholder.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import com.homework.coursework.databinding.ItemDateBinding
import com.homework.coursework.databinding.ItemMessageFromBinding
import com.homework.coursework.databinding.ItemMessageToBinding
import com.homework.coursework.databinding.ItemTopicChatBinding
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
                    ItemMessageToBinding.inflate(inflater, parent, false)
                )
                ChatViewType.MESSAGE_FROM -> MessageFromViewHolder(
                    listener,
                    ItemMessageFromBinding.inflate(inflater, parent, false)
                )
                ChatViewType.DATE -> DateViewHolder(
                    ItemDateBinding.inflate(inflater, parent, false)
                )
                ChatViewType.TOPIC_NAME -> TopicNameViewHolder(
                    ItemTopicChatBinding.inflate(inflater, parent, false)
                )
            }
        }
    }
}
