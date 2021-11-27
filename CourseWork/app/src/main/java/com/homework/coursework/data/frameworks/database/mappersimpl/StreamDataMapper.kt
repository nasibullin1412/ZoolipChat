package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.data.frameworks.database.entities.StreamEntity
import com.homework.coursework.data.frameworks.database.entities.StreamWithTopicsEntity
import com.homework.coursework.data.frameworks.database.entities.TopicEntity
import com.homework.coursework.domain.entity.StreamData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class StreamDataMapper @Inject constructor() : (List<StreamData>, Boolean) -> (List<StreamWithTopicsEntity>) {
    override fun invoke(streamList: List<StreamData>, isSubscribed: Boolean)
            : List<StreamWithTopicsEntity> {
        return streamList.map { streamData ->
            with(streamData) {
                StreamWithTopicsEntity(
                    streamEntity = StreamEntity(
                        id = 0,
                        streamBackId = id.toLong(),
                        streamName = streamName,
                        description = description,
                        dateCreated = dateCreated,
                        isSubscribed = isSubscribed
                    ),
                    topicsEntity = topics.map {
                        TopicEntity(
                            id = 0,
                            backId = it.id.toLong(),
                            topicName = it.topicName,
                            numberOfMessage = it.numberOfMess,
                            streamId = id.toLong()
                        )
                    }
                )
            }
        }
    }
}
