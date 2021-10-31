package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.presentation.adapter.data.StreamItem

class StreamItemMapper : (List<StreamData>) -> (List<StreamItem>) {

    override fun invoke(streams: List<StreamData>): List<StreamItem> {
        return streams.map { stream ->
            with(stream) {
                StreamItem(
                    id = id,
                    streamName = streamName,
                    description = description,
                    dateCreated = dateCreated,
                    topicItemList = null,
                    isTouched = false
                )
            }
        }
    }
}
