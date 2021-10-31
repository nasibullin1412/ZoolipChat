package com.homework.coursework.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.homework.coursework.databinding.TopicItemBinding
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.callbacks.TopicCallback
import com.homework.coursework.presentation.interfaces.TopicItemCallback
import com.homework.coursework.presentation.viewholder.TopicViewHolder

class TopicAdapter(private val streamItem: StreamItem) : ListAdapter<TopicItem,
        TopicViewHolder>(TopicCallback()) {

    private lateinit var listener: TopicItemCallback

    fun setListener(listener: TopicItemCallback) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        return TopicViewHolder(
            TopicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val stream = streamItem.copy(topicItemList = arrayListOf())
            listener.onTopicItemClick(getItem(position), stream)
        }
        holder.bind(getItem(position))
    }
}
