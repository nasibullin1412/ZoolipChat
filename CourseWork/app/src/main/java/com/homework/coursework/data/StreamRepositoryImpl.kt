package com.homework.coursework.data

import android.util.Log
import androidx.annotation.WorkerThread
import com.homework.coursework.data.mappers.StreamDtoMapper
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository
import com.homework.coursework.presentation.App
import io.reactivex.Observable
import kotlinx.serialization.ExperimentalSerializationApi
import kotlin.random.Random

@ExperimentalSerializationApi
class StreamRepositoryImpl : StreamRepository {

    private val streamDtoMapper: StreamDtoMapper = StreamDtoMapper()

    override fun loadStreams(): Observable<List<StreamData>> {
        return App.instance.apiService.getAllStreams()
            .switchMap {
                Observable.fromCallable {
                    streamDtoMapper(it)
                }
            }
    }

    override fun loadSubscribedStreams(): Observable<List<StreamData>> {
        return App.instance.apiService.getSubscribedStreams()
            .switchMap {
                Observable.fromCallable {
                    streamDtoMapper(it)
                }
            }
    }

    @WorkerThread
    private fun generateAllStreamList(): List<StreamData> {
        if (Random.nextInt() % 3 == 1) {
            throw IllegalArgumentException("500: Internal sever error")
        }
        Log.d("All Stream Moc", Thread.currentThread().name)
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
                description = "development stream",
                dateCreated = 100,
            ),
            StreamData(
                id = 2,
                streamName = "#Design",
                description = "design stream",
                dateCreated = 100,
            )
        )
    }


    @WorkerThread
    private fun generateSubscribedStreamList(): List<StreamData> {
        if (Random.nextInt() % 3 == 1) {
            throw IllegalArgumentException("500: Internal sever error")
        }
        Log.d("Subscribed Stream Moc", Thread.currentThread().name)
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
