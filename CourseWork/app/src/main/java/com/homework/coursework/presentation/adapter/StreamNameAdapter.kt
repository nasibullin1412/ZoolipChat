package com.homework.coursework.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homework.coursework.databinding.StreamItemBinding
import com.homework.coursework.di.StreamFragmentScope
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.callbacks.StreamCallback
import com.homework.coursework.presentation.interfaces.TopicItemCallback
import com.homework.coursework.presentation.viewholder.StreamNameViewHolder
import javax.inject.Inject

@StreamFragmentScope
class StreamNameAdapter @Inject constructor(
    private val viewPool: RecyclerView.RecycledViewPool
) : ListAdapter<StreamItem, StreamNameViewHolder>(StreamCallback()) {

    private lateinit var listenerTopic: TopicItemCallback

    fun setTopicListener(listener: TopicItemCallback) {
        this.listenerTopic = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamNameViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return StreamNameViewHolder(
            StreamItemBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StreamNameViewHolder, position: Int) {
        initChildRecycle(holder, position)
        holder.itemView.setOnClickListener {
            changeState(position)
        }
        holder.bind(getItem(position))
    }

    private fun changeState(position: Int) {
        with(getItem(position)) {
            isTouched = isTouched.not()
            notifyItemChanged(position)
        }
    }

    /**
     * Init child recycleView with topics
     * @param holder is where recycle init
     * @param position is position need for listener onTopicClick
     */
    private fun initChildRecycle(holder: StreamNameViewHolder, position: Int) {
        holder.adapterTopicName =
            TopicAdapter(getItem(position)).apply { setListener(listenerTopic) }
        with(holder.viewBinding.rvTopic) {
            layoutManager = LinearLayoutManager(context)
            adapter = holder.adapterTopicName
            setRecycledViewPool(viewPool)
        }
    }
}

