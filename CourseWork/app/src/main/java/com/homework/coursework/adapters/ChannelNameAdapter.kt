package com.homework.coursework.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homework.coursework.R
import com.homework.coursework.callbacks.ChannelCallback
import com.homework.coursework.data.ChannelData
import com.homework.coursework.interfaces.TopicItemCallback
import com.homework.coursework.viewholders.ChannelNameViewHolder

class ChannelNameAdapter : ListAdapter<ChannelData,
        ChannelNameViewHolder>(ChannelCallback()) {

    private val viewPool = RecyclerView.RecycledViewPool()
    private lateinit var listener: TopicItemCallback

    fun setListener(listener: TopicItemCallback) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelNameViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return ChannelNameViewHolder(
            inflater.inflate(R.layout.channel_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChannelNameViewHolder, position: Int) {
        initChildRecycle(holder, position)
        holder.itemView.setOnClickListener {
            getItem(position).isTouched = getItem(position).isTouched.not()
            notifyItemChanged(position)
        }
        holder.bind(getItem(position))
    }

    private fun initChildRecycle(holder: ChannelNameViewHolder, position: Int) {
        with(holder) {
            adapterTopicName = TopicAdapter(position).apply { setListener(listener) }
            with(rvTopicName) {
                layoutManager = LinearLayoutManager(context)
                adapter = adapterTopicName
                setRecycledViewPool(viewPool)
            }
        }
    }
}
