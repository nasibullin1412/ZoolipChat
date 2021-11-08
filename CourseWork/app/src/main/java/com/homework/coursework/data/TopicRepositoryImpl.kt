package com.homework.coursework.data

import android.util.Log
import androidx.annotation.WorkerThread
import com.homework.coursework.data.dto.TopicsResponse
import com.homework.coursework.data.mappers.TopicDtoMapper
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.TopicRepository
import com.homework.coursework.presentation.App
import io.reactivex.Observable
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.concurrent.TimeUnit

@ExperimentalSerializationApi
class TopicRepositoryImpl : TopicRepository {

    private val topicDtoMapper: TopicDtoMapper = TopicDtoMapper()

    override fun loadTopics(idStream: Int): Observable<List<TopicData>> {
        return App.instance.apiService.getTopics(idStream)
            .switchMap {
                Observable.fromCallable {
                    topicDtoMapper(it)
                }
            }
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
