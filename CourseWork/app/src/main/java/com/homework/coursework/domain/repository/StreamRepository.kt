package com.homework.coursework.domain.repository

import com.homework.coursework.data.dto.TopicDto
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import io.reactivex.Observable
import io.reactivex.Single

interface StreamRepository {
    fun loadStreams(): Single<List<StreamData>>
    fun loadSubscribedStreams(): Single<List<StreamData>>
    fun loadTopics(idStream: Int): Observable<List<TopicDto>>
}
