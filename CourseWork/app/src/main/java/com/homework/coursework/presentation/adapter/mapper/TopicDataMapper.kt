package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.presentation.adapter.data.TopicItem

class TopicDataMapper : (TopicItem) -> (TopicData) {
    override fun invoke(topicItem: TopicItem): TopicData {
        return with(topicItem) {
            TopicData(
                id = id,
                topicName = topicName,
                numberOfMess = numberOfMess
            )
        }
    }

    companion object {
        const val IDX_NAME_START = 1
    }
}
