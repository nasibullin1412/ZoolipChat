package com.homework.coursework.domain.repository

import com.homework.coursework.domain.entity.TopicData
import io.reactivex.Observable

interface TopicRepository {
    fun loadTopics(idStream: Int): Observable<List<TopicData>>
}
