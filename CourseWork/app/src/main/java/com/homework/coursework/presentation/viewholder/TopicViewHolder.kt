package com.homework.coursework.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.homework.coursework.R
import com.homework.coursework.databinding.TopicItemBinding
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.utils.getColorWrapper

class TopicViewHolder(private val viewBinding: TopicItemBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    private val evenColor = viewBinding.root.run {
        resources.getColorWrapper(R.color.custom_toolbar_color, context)
    }
    private val oddColor = viewBinding.root.run {
        resources.getColorWrapper(R.color.odd_color, context)
    }

    fun bind(topicItem: TopicItem) {
        with(viewBinding) {
            clTopicName.setBackgroundColor(getNeedColor(topicItem.isEven))
            tvTopicName.text = topicItem.topicName
        }
    }

    private fun getNeedColor(isEven: Boolean): Int = if (isEven) {
        evenColor
    } else {
        oddColor
    }
}
