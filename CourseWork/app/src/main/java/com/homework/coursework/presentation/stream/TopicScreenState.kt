package com.homework.coursework.presentation.stream

import com.homework.coursework.presentation.adapter.data.TopicItem

sealed class TopicScreenState {

    class Result(val data: List<TopicItem>, val position: Int) : TopicScreenState()

    object Loading : TopicScreenState()

    class Error(val error: Throwable) : TopicScreenState()
}
