package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.presentation.adapter.data.TopicItem
import dagger.Reusable
import javax.inject.Inject

@Reusable
class TopicDataMapper @Inject constructor() : (TopicItem) -> (TopicData) {
    override fun invoke(topicItem: TopicItem): TopicData {
        return with(topicItem) {
            TopicData(
                id = id,
                topicName = topicName,
                numberOfMess = numberOfMess
            )
        }
    }
}
