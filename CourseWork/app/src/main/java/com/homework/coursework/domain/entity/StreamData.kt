package com.homework.coursework.domain.entity

data class StreamData(
    val id: Int,
    val streamName: String,
    val description: String,
    val dateCreated: Int,
    var topics: List<TopicData>
)
