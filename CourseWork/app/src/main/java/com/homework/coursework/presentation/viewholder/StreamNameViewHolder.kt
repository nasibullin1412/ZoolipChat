package com.homework.coursework.presentation.viewholder

import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import com.homework.coursework.R
import com.homework.coursework.databinding.StreamItemBinding
import com.homework.coursework.presentation.adapter.TopicAdapter
import com.homework.coursework.presentation.adapter.data.StreamItem

class StreamNameViewHolder(val viewBinding: StreamItemBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    lateinit var adapterTopicName: TopicAdapter
    private val whiteColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        viewBinding.root.resources.getColor(R.color.white, viewBinding.root.context.theme)
    } else {
        viewBinding.root.resources.getColor(R.color.white)
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
