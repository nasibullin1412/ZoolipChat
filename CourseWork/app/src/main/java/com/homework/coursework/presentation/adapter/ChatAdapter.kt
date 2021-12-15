package com.homework.coursework.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.homework.coursework.di.ChatFragmentScope
import com.homework.coursework.presentation.adapter.data.chat.*
import com.homework.coursework.presentation.callbacks.MessageCallback
import com.homework.coursework.presentation.interfaces.MessageItemCallback
import com.homework.coursework.presentation.interfaces.TopicItemCallback
import com.homework.coursework.presentation.interfaces.TopicNameItemCallback
import com.homework.coursework.presentation.viewholder.chat.*
import javax.inject.Inject

@ChatFragmentScope
class ChatAdapter @Inject constructor() :
    ListAdapter<ChatItem, ChatViewHolder>(MessageCallback()) {

    private lateinit var listenerMessage: MessageItemCallback
    private lateinit var listenerTopicName: TopicNameItemCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolderFactory.create(
            viewType = viewType,
            listener = listenerMessage,
            parent = parent
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is MessageToViewHolder -> {
                if (item is MessageItem) {
                    with(holder) {
                        itemView.setOnLongClickListener { listenerMessage.getBottomSheet(item.messageId) }
                        bind(messageItem = item)
                    }
                }
            }
            is MessageFromViewHolder -> {
                if (item is MessageItem) {
                    with(holder) {
                        itemView.setOnLongClickListener { listenerMessage.getBottomSheet(item.messageId) }
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
                    with(holder) {
                        itemView.setOnClickListener {
                            listenerTopicName.onTopicItemCallback(item.topicName)
                        }
                        bind(topicName = item.topicName)
                    }
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

    fun initMessageListener(listener: MessageItemCallback) {
        this.listenerMessage = listener
    }

    fun initTopicListener(listener: TopicNameItemCallback) {
        this.listenerTopicName = listener
    }
}
