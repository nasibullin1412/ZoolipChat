package com.homework.coursework.data

import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.TopicRepository

class TopicRepositoryImpl : TopicRepository {
    override fun loadTopics(idStream: Int): List<TopicData> {
        return generateTopicList(idStream)
    }

    private fun generateTopicList(idStream: Int): List<TopicData> {
        return when (idStream) {
            0 -> {
                arrayListOf(
                    TopicData(
                        id = 0,
                        topicName = "#Testing",
                        numberOfMess = 1024
                    ),
                    TopicData(
                        id = 1,
                        topicName = "#Bruh",
                        numberOfMess = 512
                    )
                )
            }
            1 -> {
                arrayListOf(
                    TopicData(
                        id = 0,
                        topicName = "#Loling",
                        numberOfMess = 1024
                    ),
                    TopicData(
                        id = 1,
                        topicName = "#Keking",
                        numberOfMess = 512
                    )
                )
            }
            2 -> {
                arrayListOf(
                    TopicData(
                        id = 0,
                        topicName = "#Feature",
                        numberOfMess = 1024
                    ),
                    TopicData(
                        id = 1,
                        topicName = "#Testing",
                        numberOfMess = 10
                    )
                )
            }
            else -> throw IllegalArgumentException("Unexpected idStream")
        }
    }
}
