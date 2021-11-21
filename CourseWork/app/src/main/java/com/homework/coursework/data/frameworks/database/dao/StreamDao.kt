package com.homework.coursework.data.frameworks.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Query
import com.homework.coursework.data.frameworks.database.entities.StreamEntity
import com.homework.coursework.data.frameworks.database.entities.StreamWithTopicsEntity
import com.homework.coursework.data.frameworks.database.entities.TopicEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface StreamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopic(topicEntity: TopicEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStream(streamEntity: StreamEntity)

    @Transaction
    fun insertOneStream(streamWithTopics: StreamWithTopicsEntity) {
        insertStream(streamWithTopics.streamEntity)
        for (topic in streamWithTopics.topicsEntity) {
            insertTopic(topic)
        }
    }

    fun insertStreams(streamsWithTopics: List<StreamWithTopicsEntity>): Completable {
        for (stream in streamsWithTopics) {
            insertOneStream(stream)
        }
        return Completable.complete()
    }

    @Query("SELECT * FROM stream_table")
    fun getAllStreams(): Single<List<StreamWithTopicsEntity>>

    @Query("SELECT * FROM stream_table WHERE is_subscribed = 1")
    fun getSubscribedStreams(): Single<List<StreamWithTopicsEntity>>
}
