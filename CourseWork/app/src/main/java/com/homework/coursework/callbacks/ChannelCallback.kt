package com.homework.coursework.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.homework.coursework.data.ChannelData

class ChannelCallback : DiffUtil.ItemCallback<ChannelData>() {

    override fun areItemsTheSame(oldItem: ChannelData, newItem: ChannelData)
            : Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ChannelData, newItem: ChannelData)
            : Boolean = oldItem == newItem
}
