package com.homework.coursework.domain.repository

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface MessageRepository {
    fun initMessages(streamData: StreamData, topicData: TopicData): Observable<List<MessageData>>
    fun loadMessages(streamData: StreamData, topicData: TopicData): Observable<List<MessageData>>
    fun addMessages(streamData: StreamData, topicData: TopicData, content: String): Observable<Int>
}
