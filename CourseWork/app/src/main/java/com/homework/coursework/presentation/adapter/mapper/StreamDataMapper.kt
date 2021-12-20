package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.presentation.adapter.data.StreamItem
import dagger.Reusable
import javax.inject.Inject

@Reusable
class StreamDataMapper @Inject constructor() : (StreamItem) -> (StreamData) {

    override fun invoke(streamItem: StreamItem) = streamItem.run {
        StreamData(
            id = id,
            streamName = streamName.substring(IDX_NAME_START),
            description = description,
            dateCreated = dateCreated,
            topics = listOf()
        )
    }

    companion object {
        const val IDX_NAME_START = 1
    }
}
