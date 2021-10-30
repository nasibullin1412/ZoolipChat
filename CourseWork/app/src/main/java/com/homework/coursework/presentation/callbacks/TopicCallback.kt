package com.homework.coursework.presentation.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.homework.coursework.domain.entity.TopicData

class TopicCallback : DiffUtil.ItemCallback<TopicData>() {

    override fun areItemsTheSame(oldItem: TopicData, newItem: TopicData)
            : Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TopicData, newItem: TopicData)
            : Boolean = oldItem == newItem
}
