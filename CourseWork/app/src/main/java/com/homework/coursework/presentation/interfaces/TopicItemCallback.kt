package com.homework.coursework.presentation.interfaces

import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem

interface TopicItemCallback {
    fun onTopicItemClick(topic: TopicItem, stream: StreamItem)
}
