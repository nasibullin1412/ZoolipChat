package com.homework.coursework.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.homework.coursework.di.ChatFragmentScope
import com.homework.coursework.presentation.adapter.data.chat.ErrorItem
import com.homework.coursework.presentation.adapter.data.chat.ChatItem
import com.homework.coursework.presentation.adapter.data.chat.DateItem
import com.homework.coursework.presentation.adapter.data.chat.MessageItem
import com.homework.coursework.presentation.adapter.data.chat.TopicNameItem
import com.homework.coursework.presentation.callbacks.MessageCallback
import com.homework.coursework.presentation.interfaces.MessageItemCallback
import com.homework.coursework.presentation.viewholder.chat.*
import javax.inject.Inject

@ChatFragmentScope
class ChatAdapter @Inject constructor() :
    ListAdapter<ChatItem, ChatViewHolder>(MessageCallback()) {

    private lateinit var listener: MessageItemCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolderFactory.create(
            viewType = viewType,
            listener = listener,
            parent = parent
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is MessageToViewHolder -> {
                if (item is MessageItem) {
                    holder.apply {
                        itemView.setOnLongClickListener { listener.getBottomSheet(item.messageId) }
                        bind(messageItem = item)
                    }
                }
            }
            is MessageFromViewHolder -> {
                if (item is MessageItem) {
                    holder.apply {
                        itemView.setOnLongClickListener { listener.getBottomSheet(item.messageId) }
                        bind(messageItem = item)
                    }
                }
            }
            is DateViewHolder -> {
                if (item is DateItem) {
                    holder.bind(date = item.date)
                }
                return
            }
            is TopicNameViewHolder -> {
                if (item is TopicNameItem) {
                    holder.bind(topicName = item.topicName)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (val item = getItem(position)) {
            is DateItem -> {
                ChatViewType.DATE.value
            }
            is MessageItem -> {
                if (item.isCurrentUserMessage) {
                    ChatViewType.MESSAGE_TO.value
                } else {
                    ChatViewType.MESSAGE_FROM.value
                }
            }
            is TopicNameItem -> {
                ChatViewType.TOPIC_NAME.value
            }
            is ErrorItem -> {
                throw IllegalArgumentException("Unexpected item type")
            }
        }
    }

    fun initListener(listener: MessageItemCallback) {
        this.listener = listener
    }
}
