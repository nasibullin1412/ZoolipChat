package com.homework.coursework.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homework.coursework.R
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.callbacks.StreamCallback
import com.homework.coursework.presentation.interfaces.StreamItemCallback
import com.homework.coursework.presentation.interfaces.TopicItemCallback
import com.homework.coursework.presentation.viewholder.StreamNameViewHolder

class StreamNameAdapter : ListAdapter<StreamItem,
        StreamNameViewHolder>(StreamCallback()) {

    private val viewPool = RecyclerView.RecycledViewPool()
    private lateinit var listenerTopic: TopicItemCallback
    private lateinit var listenerStream: StreamItemCallback

    fun setTopicListener(listener: TopicItemCallback) {
        this.listenerTopic = listener
    }

    fun setStreamListener(listener: StreamItemCallback) {
        this.listenerStream = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamNameViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return StreamNameViewHolder(
            inflater.inflate(R.layout.channel_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StreamNameViewHolder, position: Int) {
        initChildRecycle(holder, position)
        holder.itemView.setOnClickListener {
            getItem(position).isTouched = getItem(position).isTouched.not()
            listenerStream.onStreamItemCallback(position)
        }
        holder.bind(getItem(position))
    }

    /**
     * Init child recycleView with topics
     * @param holder is where recycle init
     * @param position is position need for listener onTopicClick
     */
    private fun initChildRecycle(holder: StreamNameViewHolder, position: Int) {
        with(holder) {
            adapterTopicName = TopicAdapter(getItem(position)).apply { setListener(listenerTopic) }
            with(rvTopicName) {
                layoutManager = LinearLayoutManager(context)
                adapter = adapterTopicName
                setRecycledViewPool(viewPool)
            }
        }
    }
}
