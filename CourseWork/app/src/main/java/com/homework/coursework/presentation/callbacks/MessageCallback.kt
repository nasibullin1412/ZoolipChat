package com.homework.coursework.presentation.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.homework.coursework.presentation.adapter.data.MessageItem

class MessageCallback : DiffUtil.ItemCallback<MessageItem>() {
    override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
        return oldItem == newItem
    }
}
