package com.homework.coursework.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.homework.coursework.data.MessageData

class MessageCallback : DiffUtil.ItemCallback<MessageData>() {
    override fun areItemsTheSame(oldItem: MessageData, newItem: MessageData): Boolean {
        return oldItem.messageId == newItem.messageId
    }

    override fun areContentsTheSame(oldItem: MessageData, newItem: MessageData): Boolean {
        return oldItem == newItem
    }
}
