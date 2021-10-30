package com.homework.coursework.presentation.stream

import com.homework.coursework.presentation.adapter.data.StreamItem

sealed class StreamScreenState {

    class Result(val items: List<StreamItem>) : StreamScreenState()

    object Loading : StreamScreenState()

    class Error(val error: Throwable) : StreamScreenState()
}
