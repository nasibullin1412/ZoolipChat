package com.homework.coursework.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.homework.coursework.R
import com.homework.coursework.callbacks.TopicCallback
import com.homework.coursework.data.ChannelTopic
import com.homework.coursework.viewholders.TopicViewHolder

class TopicAdapter : ListAdapter<ChannelTopic,
        TopicViewHolder>(TopicCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return TopicViewHolder(
            inflater.inflate(R.layout.topic_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}
