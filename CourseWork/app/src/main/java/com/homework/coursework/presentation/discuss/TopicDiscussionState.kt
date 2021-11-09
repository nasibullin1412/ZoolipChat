package com.homework.coursework.presentation.discuss

import com.homework.coursework.presentation.adapter.data.DiscussItem

sealed class TopicDiscussionState {

    class ResultMessages(val data: List<DiscussItem>) : TopicDiscussionState()

    object ResultUserChanges : TopicDiscussionState()

    object Loading : TopicDiscussionState()

    class Error(val error: Throwable) : TopicDiscussionState()

    class ErrorUserChanges(val error: Throwable): TopicDiscussionState()
}
