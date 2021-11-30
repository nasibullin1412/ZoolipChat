package com.homework.coursework.data

import android.util.Log
import androidx.room.EmptyResultSetException
import com.homework.coursework.data.dto.StreamDto
import com.homework.coursework.data.dto.StreamWithTopics
import com.homework.coursework.data.dto.TopicDto
import com.homework.coursework.data.frameworks.database.dao.StreamDao
import com.homework.coursework.data.frameworks.database.entities.StreamWithTopicsEntity
import com.homework.coursework.data.frameworks.database.mappersimpl.StreamDataMapper
import com.homework.coursework.data.frameworks.database.mappersimpl.StreamEntityMapper
import com.homework.coursework.data.frameworks.network.ApiService
import com.homework.coursework.data.frameworks.network.mappersimpl.StreamDtoMapper
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository
import dagger.Lazy
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class StreamRepositoryImpl @Inject constructor(
    private val _apiService: Lazy<ApiService>,
    private val streamDao: StreamDao,
) : StreamRepository {

    @Inject
    internal lateinit var streamDtoMapper: StreamDtoMapper

    @Inject
    internal lateinit var streamDataMapper: StreamDataMapper

    @Inject
    internal lateinit var streamEntityMapper: StreamEntityMapper

    @Inject
    internal lateinit var compositeDisposable: CompositeDisposable

    private val apiService: ApiService get() = _apiService.get()

    override fun loadAllStreams(): Observable<List<StreamData>> {
        return Observable.concatArrayEagerDelayError(
            getLocalAllStreams(),
            getRemoteAllStreams()
        )

    }

    private fun getRemoteAllStreams(): Observable<List<StreamData>> {
        return apiService.getAllStreams()
            .subscribeOn(Schedulers.io())
            .switchMap { streamList -> Observable.fromIterable(streamList.data) }
            .concatMap { streamDto -> zipStreamAndTopics(streamDto) }
            .toList()
            .map(streamDtoMapper)
            .doAfterSuccess { storeStreamsInDb(streamDataMapper(it, isSubscribed = false)) }
            .toObservable()
            .onErrorReturn { error: Throwable ->
                listOf(
                    StreamData.getErrorObject(error)
                )
            }
    }

    private fun getLocalAllStreams(): Observable<List<StreamData>> {
        return streamDao.getAllStreams()
            .map { if (it.isEmpty()) throw EmptyResultSetException("empty db") else it }
            .map(streamEntityMapper)
            .toObservable()
            .onErrorReturn { error: Throwable ->
                listOf(
                    StreamData.getErrorObject(error)
                )
            }
    }

    override fun loadSubscribedStreams(): Observable<List<StreamData>> {
        return Observable.concatArrayEagerDelayError(
            getLocalSubscribedStreams(),
            getRemoteSubscribedStreams()
        )
    }


    private fun getRemoteSubscribedStreams(): Observable<List<StreamData>> {
        return apiService.getSubscribedStreams()
            .switchMap { streamList -> Observable.fromIterable(streamList.data) }
            .concatMap { streamDto -> zipStreamAndTopics(streamDto) }
            .toList()
            .map(streamDtoMapper)
            .doAfterSuccess { storeStreamsInDb(streamDataMapper(it, isSubscribed = true)) }
            .toObservable()
            .onErrorReturn { error: Throwable ->
                listOf(
                    StreamData.getErrorObject(error)
                )
            }
    }

    private fun getLocalSubscribedStreams(): Observable<List<StreamData>> {
        return streamDao.getSubscribedStreams()
            .map { if (it.isEmpty()) throw EmptyResultSetException("empty db") else it }
            .map(streamEntityMapper)
            .toObservable()
            .onErrorReturn { error: Throwable ->
                listOf(
                    StreamData.getErrorObject(error)
                )
            }
    }

    private fun loadTopics(idStream: Int): Observable<List<TopicDto>> {
        return apiService.getTopics(idStream)
            .map { it.data }
    }

    private fun storeStreamsInDb(streamWithTopics: List<StreamWithTopicsEntity>) {
        Observable.fromCallable {
            streamDao.insertStreams(streamWithTopics)
                .doOnError {
                    Log.d("FromRoom", it.message.toString())
                }
        }
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onNext = { Log.d("FromRoom", it.toString()) },
                onError = { Log.d("FromRoom", it.message.toString()) }
            )
            .addTo(compositeDisposable)
    }

    private fun zipStreamAndTopics(streamDto: StreamDto): Observable<StreamWithTopics> {
        return Observable.zip(
            Observable.just(streamDto),
            loadTopics(streamDto.streamId)
        ) { stream: StreamDto, topicList: List<TopicDto> -> Pair(stream, topicList) }
    }
}
