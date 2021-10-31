package com.homework.coursework.data

import androidx.annotation.WorkerThread
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.StreamRepository

class StreamRepositoryImpl : StreamRepository {
    override fun loadStreams(): List<StreamData> {
        return generateAllStreamList()
    }

    override fun loadSubscribedStreams(): List<StreamData> {
        return generateSubscribedStreamList()
    }

    @WorkerThread
    private fun generateAllStreamList(): List<StreamData> {
        return listOf(
            StreamData(
                id = 0,
                streamName = "#general",
                description = "general stream",
                dateCreated = 100,
            ),
            StreamData(
                id = 1,
                streamName = "#Development",
                description = "testing stream",
                dateCreated = 100,
            ),
            StreamData(
                id = 2,
                streamName = "#Design",
                description = "general stream",
                dateCreated = 100,
            )
        )
    }


    @WorkerThread
    private fun generateSubscribedStreamList(): List<StreamData> {
        return listOf(
            StreamData(
                id = 0,
                streamName = "#general",
                description = "general stream",
                dateCreated = 100,
            ),
            StreamData(
                id = 1,
                streamName = "#Development",
                description = "testing stream",
                dateCreated = 100,
            )
        )
    }
}
