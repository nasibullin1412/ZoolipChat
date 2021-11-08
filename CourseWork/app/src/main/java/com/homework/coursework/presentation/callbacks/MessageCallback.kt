package com.homework.coursework.presentation.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.homework.coursework.presentation.adapter.data.DiscussItem

class MessageCallback : DiffUtil.ItemCallback<DiscussItem>() {
    override fun areItemsTheSame(oldItem: DiscussItem, newItem: DiscussItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DiscussItem, newItem: DiscussItem): Boolean {
        return oldItem == newItem
    }
}
