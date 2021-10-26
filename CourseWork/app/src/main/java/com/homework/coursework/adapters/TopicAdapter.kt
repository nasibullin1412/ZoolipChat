package com.homework.coursework.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.homework.coursework.R
import com.homework.coursework.callbacks.TopicCallback
import com.homework.coursework.data.ChannelTopic
import com.homework.coursework.interfaces.TopicItemCallback
import com.homework.coursework.viewholders.TopicViewHolder

class TopicAdapter(private val idChannel: Int) : ListAdapter<ChannelTopic,
        TopicViewHolder>(TopicCallback()) {

    private lateinit var listener: TopicItemCallback

    fun setListener(listener: TopicItemCallback){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return TopicViewHolder(
            inflater.inflate(R.layout.topic_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            listener.onTopicItemClick(getItem(position).id, idChannel)
        }
        holder.bind(getItem(position), position)
    }
}
