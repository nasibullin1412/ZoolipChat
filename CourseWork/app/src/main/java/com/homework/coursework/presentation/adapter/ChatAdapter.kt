package com.homework.coursework.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.homework.coursework.databinding.DateItemBinding
import com.homework.coursework.databinding.MessageFromItemBinding
import com.homework.coursework.databinding.MessageToItemBinding
import com.homework.coursework.di.ChatFragmentScope
import com.homework.coursework.presentation.adapter.data.ChatItem
import com.homework.coursework.presentation.adapter.data.DateItem
import com.homework.coursework.presentation.adapter.data.ErrorItem
import com.homework.coursework.presentation.adapter.data.MessageItem
import com.homework.coursework.presentation.callbacks.MessageCallback
import com.homework.coursework.presentation.interfaces.MessageItemCallback
import com.homework.coursework.presentation.viewholder.chat.*
import java.lang.IllegalArgumentException
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
                        bind(messageItem = item)
                        itemView.setOnClickListener { listener.getBottomSheet(item.messageId) }
                    }
                }
            }
            is MessageFromViewHolder -> {
                if (item is MessageItem) {
                    holder.apply {
                        bind(messageItem = item)
                        itemView.setOnClickListener { listener.getBottomSheet(item.messageId) }
                    }
                }
            }
            is DateViewHolder -> {
                if (item is DateItem) {
                    holder.bind(date = item.date)
                }
                return
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
            is ErrorItem -> {
                throw IllegalArgumentException("Unexpected item type")
            }
        }
    }

    fun initListener(listener: MessageItemCallback) {
        this.listener = listener
    }
}
