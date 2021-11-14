package com.homework.coursework.data.frameworks.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class StreamWithTopicsEntity(
    @Embedded val streamEntity: StreamEntity,
    @Relation(parentColumn = "stream_back_id", entityColumn = "stream_id")
    val topicsEntity: List<TopicEntity>
)
