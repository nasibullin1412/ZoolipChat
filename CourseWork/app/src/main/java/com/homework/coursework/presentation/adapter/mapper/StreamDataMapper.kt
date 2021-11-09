package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.presentation.adapter.data.StreamItem

class StreamDataMapper : (StreamItem) -> (StreamData) {

    override fun invoke(streamItem: StreamItem): StreamData {
        return with(streamItem) {
            StreamData(
                id = id,
                streamName = streamName.substring(IDX_NAME_START),
                description = description,
                dateCreated = dateCreated
            )
        }
    }

    companion object {
        const val IDX_NAME_START = 1
    }
}
