package com.homework.coursework.data

import android.util.Log
import androidx.annotation.WorkerThread
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class StreamRepositoryImpl : StreamRepository {
    override fun loadStreams(): Observable<List<StreamData>> {
        return Observable.fromCallable { generateAllStreamList() }
            .delay(2000L, TimeUnit.MILLISECONDS)
    }

    override fun loadSubscribedStreams(): Observable<List<StreamData>> {
        return Observable.fromCallable { generateSubscribedStreamList() }
            .delay(2000L, TimeUnit.MILLISECONDS)
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
