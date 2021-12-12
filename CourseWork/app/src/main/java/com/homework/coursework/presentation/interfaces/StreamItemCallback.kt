package com.homework.coursework.presentation.interfaces

import com.homework.coursework.presentation.adapter.data.StreamItem

interface StreamItemCallback {
    fun onStreamItemLongClick(streamItem: StreamItem)
}
