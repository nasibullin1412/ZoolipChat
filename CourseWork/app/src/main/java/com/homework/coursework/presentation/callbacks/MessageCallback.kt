package com.homework.coursework.presentation.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.homework.coursework.presentation.adapter.data.chat.ChatItem
import com.homework.coursework.presentation.adapter.data.chat.DateItem
import com.homework.coursework.presentation.adapter.data.chat.MessageItem
import com.homework.coursework.presentation.adapter.data.chat.TopicNameItem

class MessageCallback : DiffUtil.ItemCallback<ChatItem>() {
    override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
        if (oldItem is MessageItem && newItem is MessageItem) {
            return oldItem == newItem
        }
        if (oldItem is DateItem && newItem is DateItem) {
            return oldItem == newItem
        }
        if (oldItem is TopicNameItem && newItem is TopicNameItem) {
            return oldItem == newItem
        }
        return oldItem == newItem
    }
}
