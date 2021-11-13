package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.presentation.adapter.data.StreamItem

class StreamItemMapper : (List<Pair<StreamData, List<TopicData>>>) -> (List<StreamItem>) {

    private val topicItemMapper: TopicItemMapper = TopicItemMapper()

    override fun invoke(streamsPair: List<Pair<StreamData, List<TopicData>>>): List<StreamItem> {
        return streamsPair.map { streamPair ->
            with(streamPair) {
                StreamItem(
                    id = first.id,
                    streamName = "#${first.streamName}",
                    description = first.description,
                    dateCreated = first.dateCreated,
                    topicItemList = topicItemMapper(second),
                    isTouched = false
                )
            }
        }
    }
}
