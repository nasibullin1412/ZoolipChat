package com.homework.coursework.data

import android.util.Log
import androidx.annotation.WorkerThread
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.TopicRepository
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class TopicRepositoryImpl : TopicRepository {
    override fun loadTopics(idStream: Int): Observable<List<TopicData>> {
        return Observable.fromCallable { generateTopicList(idStream) }
            .delay(1000L, TimeUnit.MILLISECONDS)
    }

    @WorkerThread
    private fun generateTopicList(idStream: Int): List<TopicData> {
        Log.d("Topic moc", Thread.currentThread().name)
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
