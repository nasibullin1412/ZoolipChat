package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.presentation.adapter.data.ErrorHandle
import com.homework.coursework.presentation.adapter.data.StreamItem
import dagger.Reusable
import javax.inject.Inject

@Reusable
class StreamItemMapper @Inject constructor() : (List<StreamData>) -> (List<StreamItem>) {

    @Inject
    internal lateinit var topicItemMapper: TopicItemMapper

    override fun invoke(streams: List<StreamData>): List<StreamItem> {
        return streams.map { stream ->
            with(stream) {
                StreamItem(
                    id = id,
                    streamName = "#$streamName",
                    description = description,
                    dateCreated = dateCreated,
                    topicItemList = topicItemMapper(stream.topics),
                    isTouched = false,
                    errorHandle = ErrorHandle(
                        isError = errorHandle.isError,
                        error = errorHandle.error
                    )
                )
            }
        }
    }
}
