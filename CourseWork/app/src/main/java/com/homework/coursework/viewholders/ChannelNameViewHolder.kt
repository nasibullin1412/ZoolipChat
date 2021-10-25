package com.homework.coursework.viewholders

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.homework.coursework.R
import com.homework.coursework.adapters.TopicAdapter
import com.homework.coursework.data.ChannelData

class ChannelNameViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val tvTopicName = view.findViewById<TextView>(R.id.tvChannelName)
    private val imgDropDown = view.findViewById<ImageView>(R.id.imgDropDown)
    val rvTopicName: RecyclerView = view.findViewById(R.id.rvTopic)
    lateinit var adapterTopicName: TopicAdapter
    private val whiteColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        view.resources.getColor(R.color.white, view.context.theme)
    } else {
        view.resources.getColor(R.color.white)
    }

    fun bind(channelData: ChannelData) {
        tvTopicName.text = channelData.channelName
        if (channelData.isTouched) {
            imgDropDown.setImageResource(R.drawable.ic_spinner_drop)
            tvTopicName.setTextColor(whiteColor)
        } else {
            imgDropDown.setImageResource(R.drawable.ic_spinner)
        }
        if (channelData.isTouched) {
            adapterTopicName.submitList(channelData.topicList)
        } else {
            adapterTopicName.submitList(null)
        }
    }
}
