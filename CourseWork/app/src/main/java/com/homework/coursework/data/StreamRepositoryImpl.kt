package com.homework.coursework.data

import android.util.Log
import androidx.annotation.WorkerThread
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class StreamRepositoryImpl : StreamRepository {
    override fun loadStreams(): Observable<List<StreamData>> {
        return Observable.fromCallable { generateAllStreamList() }
            .delay(1000L, TimeUnit.MILLISECONDS)
    }

    override fun loadSubscribedStreams(): Observable<List<StreamData>> {
        return Observable.fromCallable { generateSubscribedStreamList() }
            .delay(1000L, TimeUnit.MILLISECONDS)
    }

    @WorkerThread
    private fun generateAllStreamList(): List<StreamData> {
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
