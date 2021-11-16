package com.homework.coursework.data.mappers

import com.homework.coursework.domain.entity.MessageData

interface MessageMapper<T> : (T) -> (List<MessageData>) {
    override fun invoke(messages: T): List<MessageData>
}
