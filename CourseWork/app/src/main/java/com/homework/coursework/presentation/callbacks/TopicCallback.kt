package com.homework.coursework.presentation.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.homework.coursework.presentation.adapter.data.TopicItem

class TopicCallback : DiffUtil.ItemCallback<TopicItem>() {

    override fun areItemsTheSame(oldItem: TopicItem, newItem: TopicItem)
            : Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TopicItem, newItem: TopicItem)
            : Boolean = oldItem == newItem
}
