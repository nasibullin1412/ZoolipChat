package com.homework.coursework.presentation.interfaces

import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem

interface AddTopicDiscussion {
    fun addTopicDiscussion(topic: TopicItem?, stream: StreamItem?)
}
