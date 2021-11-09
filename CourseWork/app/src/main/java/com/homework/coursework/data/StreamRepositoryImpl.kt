package com.homework.coursework.data

import com.homework.coursework.data.mappers.StreamDtoMapper
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.repository.StreamRepository
import com.homework.coursework.presentation.App
import io.reactivex.Observable
import kotlinx.serialization.ExperimentalSerializationApi

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
}
