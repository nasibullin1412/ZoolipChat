package com.homework.coursework.presentation.discuss

import com.homework.coursework.presentation.adapter.data.MessageItem

sealed class TopicDiscussionState {

    class Result(val data: List<MessageItem>) : TopicDiscussionState()

    object Loading : TopicDiscussionState()

    class Error(val error: Throwable) : TopicDiscussionState()
}
