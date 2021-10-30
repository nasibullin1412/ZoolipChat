package com.homework.coursework.presentation.viewholder

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.homework.coursework.R
import com.homework.coursework.presentation.adapter.TopicAdapter
import com.homework.coursework.presentation.adapter.data.StreamItem

class StreamNameViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val tvTopicName = view.findViewById<TextView>(R.id.tvChannelName)
    private val imgDropDown = view.findViewById<ImageView>(R.id.imgDropDown)
    val rvTopicName: RecyclerView = view.findViewById(R.id.rvTopic)
    lateinit var adapterTopicName: TopicAdapter
    private val whiteColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        view.resources.getColor(R.color.white, view.context.theme)
    } else {
        view.resources.getColor(R.color.white)
    }

    fun bind(streamData: StreamItem) {
        tvTopicName.text = streamData.streamName
        if (streamData.isTouched) {
            imgDropDown.setImageResource(R.drawable.ic_spinner_drop)
            tvTopicName.setTextColor(whiteColor)
            adapterTopicName.submitList(streamData.topicDataList)
            return
        }
        imgDropDown.setImageResource(R.drawable.ic_spinner)
        adapterTopicName.submitList(null)
    }
}
