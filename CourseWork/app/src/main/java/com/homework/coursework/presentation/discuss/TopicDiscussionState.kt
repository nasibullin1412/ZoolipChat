package com.homework.coursework.presentation.discuss

import com.homework.coursework.presentation.adapter.data.DiscussItem

sealed class TopicDiscussionState {

    class Result(val data: List<DiscussItem>) : TopicDiscussionState()

    object Loading : TopicDiscussionState()

    class Error(val error: Throwable) : TopicDiscussionState()
}
