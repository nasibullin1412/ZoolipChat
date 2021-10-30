package com.homework.coursework.domain.repository

import com.homework.coursework.domain.entity.MessageData

interface MessageRepository {
    fun loadMessages(idTopic: Int): List<MessageData>
}
