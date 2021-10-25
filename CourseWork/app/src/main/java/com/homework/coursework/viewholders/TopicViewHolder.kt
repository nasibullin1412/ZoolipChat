package com.homework.coursework.viewholders

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.homework.coursework.R
import com.homework.coursework.data.ChannelTopic

class TopicViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val tvNameTopic = view.findViewById<TextView>(R.id.tvTopicName)
    private val tvNumberOfMess = view.findViewById<TextView>(R.id.tvNumberOfMess)
    private val cvTopic = view.findViewById<ConstraintLayout>(R.id.clTopicName)
    private val evenColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        view.resources.getColor(R.color.custom_bar_color, view.context.theme)
    } else {
        view.resources.getColor(R.color.custom_bar_color)
    }
    private val oddColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        view.resources.getColor(R.color.odd_color, view.context.theme)
    } else {
        view.resources.getColor(R.color.odd_color)
    }


    @SuppressLint("SetTextI18n")
    fun bind(channelTopic: ChannelTopic, position: Int) {
        cvTopic.setBackgroundColor(getNeedColor(position))
        tvNameTopic.text = channelTopic.topicName
        tvNumberOfMess.text = "${channelTopic.numberOfMess} mes"
    }

    private fun getNeedColor(position: Int): Int = if (position % 2 == 0) {
        evenColor
    } else {
        oddColor
    }
}
