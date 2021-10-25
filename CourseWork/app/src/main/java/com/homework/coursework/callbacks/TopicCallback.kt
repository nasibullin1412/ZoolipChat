package com.homework.coursework.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.homework.coursework.data.ChannelTopic

class TopicCallback : DiffUtil.ItemCallback<ChannelTopic>() {

    override fun areItemsTheSame(oldItem: ChannelTopic, newItem: ChannelTopic)
            : Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ChannelTopic, newItem: ChannelTopic)
            : Boolean = oldItem == newItem
}