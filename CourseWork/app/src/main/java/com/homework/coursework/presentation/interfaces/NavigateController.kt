package com.homework.coursework.presentation.interfaces

import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem

interface NavigateController {
    fun navigateFragment(topic: TopicItem, stream: StreamItem)
    fun navigateFragment()
}
