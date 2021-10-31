package com.homework.coursework.domain.repository

import com.homework.coursework.domain.entity.MessageData
import io.reactivex.Observable

interface MessageRepository {
    fun loadMessages(idStream: Int, idTopic: Int): Observable<List<MessageData>>
}
