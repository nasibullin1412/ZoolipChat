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
import com.homework.coursework.presentation.viewholder.chat.ChatViewHolder
import com.homework.coursework.presentation.viewholder.chat.DateViewHolder
import com.homework.coursework.presentation.viewholder.chat.MessageFromViewHolder
import com.homework.coursework.presentation.viewholder.chat.MessageToViewHolder
import java.lang.IllegalArgumentException
import javax.inject.Inject

@ChatFragmentScope
class ChatAdapter @Inject constructor() :
    ListAdapter<ChatItem, ChatViewHolder>(MessageCallback()) {

    private lateinit var listener: MessageItemCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            MESSAGE_TO -> MessageToViewHolder(
                listener,
                MessageToItemBinding.inflate(inflater, parent, false)
            )
            MESSAGE_FROM -> MessageFromViewHolder(
                listener,
                MessageFromItemBinding.inflate(inflater, parent, false)
            )
            else -> DateViewHolder(
                DateItemBinding.inflate(inflater, parent, false)
            )
        }
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
                DATE
            }
            is MessageItem -> {
                if (item.isCurrentUserMessage) {
                    MESSAGE_TO
                } else {
                    MESSAGE_FROM
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

    companion object {
        private const val MESSAGE_TO = 0
        private const val MESSAGE_FROM = 1
        private const val DATE = 2
    }
}
