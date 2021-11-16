package com.homework.coursework.data.mappers

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.entity.StreamData

interface MessageMapper<T> : (T) -> (List<MessageData>) {
    override fun invoke(messages: T): List<MessageData>
}
