package com.homework.coursework.data

data class ChannelData(
    val id: Int,
    val channelName: String,
    val topicList: ArrayList<ChannelTopic>
)