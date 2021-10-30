package com.homework.coursework.presentation.adapter.data

import com.homework.coursework.domain.entity.TopicData

data class StreamItem(
    val id: Int,
    val streamName: String,
    val description: String,
    val dateCreated: Int,
    val topicDataList: ArrayList<TopicData>,
    var isTouched: Boolean
)