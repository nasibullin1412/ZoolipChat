package com.homework.coursework.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.homework.coursework.R
import com.homework.coursework.databinding.StreamItemBinding
import com.homework.coursework.presentation.adapter.TopicAdapter
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.utils.getColorWrapper

class StreamNameViewHolder(val viewBinding: StreamItemBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    lateinit var adapterTopicName: TopicAdapter

    private val whiteColor = viewBinding.root.run {
        resources.getColorWrapper(R.color.white, context)
    }

    fun bind(streamData: StreamItem) {
        with(viewBinding) {
            tvChannelName.text = streamData.streamName
            if (streamData.isTouched.not()) {
                imgDropDown.setImageResource(R.drawable.ic_spinner)
                adapterTopicName.submitList(null)
                return
            }
            imgDropDown.setImageResource(R.drawable.ic_spinner_drop)
            tvChannelName.setTextColor(whiteColor)
        }
        adapterTopicName.submitList(streamData.topicItemList)
    }
}
