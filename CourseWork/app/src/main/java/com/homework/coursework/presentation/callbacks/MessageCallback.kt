package com.homework.coursework.presentation.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.homework.coursework.presentation.adapter.data.ChatItem
import com.homework.coursework.presentation.adapter.data.DateItem
import com.homework.coursework.presentation.adapter.data.MessageItem

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
        return oldItem == newItem
    }
}
