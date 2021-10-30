package com.homework.coursework.presentation.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.homework.coursework.presentation.adapter.data.StreamItem

class StreamCallback : DiffUtil.ItemCallback<StreamItem>() {

    override fun areItemsTheSame(oldItem: StreamItem, newItem: StreamItem)
            : Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: StreamItem, newItem: StreamItem)
            : Boolean = oldItem == newItem
}
