package com.homework.coursework.presentation.viewholder

import android.annotation.SuppressLint
import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import com.homework.coursework.R
import com.homework.coursework.databinding.TopicItemBinding
import com.homework.coursework.presentation.adapter.data.TopicItem

class TopicViewHolder(private val viewBinding: TopicItemBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    private val evenColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        viewBinding.root.resources.getColor(
            R.color.custom_bar_color,
            viewBinding.root.context.theme
        )
    } else {
        viewBinding.root.resources.getColor(R.color.custom_bar_color)
    }
    private val oddColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        viewBinding.root.resources.getColor(R.color.odd_color, viewBinding.root.context.theme)
    } else {
        viewBinding.root.resources.getColor(R.color.odd_color)
    }

    @SuppressLint("SetTextI18n")
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
