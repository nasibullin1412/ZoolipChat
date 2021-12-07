package com.homework.coursework.presentation.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.homework.coursework.presentation.adapter.data.ChatItem

class MessageCallback : DiffUtil.ItemCallback<ChatItem>() {
    override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
        return oldItem == newItem
    }
}
