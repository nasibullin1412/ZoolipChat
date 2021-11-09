package com.homework.coursework.domain.repository

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import io.reactivex.Completable
import io.reactivex.Observable

interface MessageRepository {
    fun loadMessages(streamData: StreamData, topicData: TopicData): Observable<List<MessageData>>
    fun addMessages(streamData: StreamData, topicData: TopicData, content: String): Completable
}
