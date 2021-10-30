package com.homework.coursework.domain.entity

data class StreamData(
    val id: Int,
    val streamName: String,
    val description: String,
    val dateCreated: Int,
    val topicDataList: ArrayList<TopicData>
)
