package com.homework.coursework.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.homework.coursework.data.BaseItem

class MessageCallback : DiffUtil.ItemCallback<BaseItem>() {
    override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return oldItem == newItem
    }
}
