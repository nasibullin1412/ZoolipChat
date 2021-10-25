package com.homework.coursework.data

data class ChannelData(
    val id: Int,
    val channelName: String,
    var isTouched: Boolean,
    val topicList: ArrayList<ChannelTopic>
)