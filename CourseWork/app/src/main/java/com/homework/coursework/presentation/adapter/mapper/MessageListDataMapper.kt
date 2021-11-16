package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.presentation.adapter.data.DiscussItem


class MessageListDataMapper : (List<DiscussItem>) -> (List<MessageData>) {

    private val messageDataMapper: MessageDataMapper = MessageDataMapper()

    override fun invoke(discussItemList: List<DiscussItem>): List<MessageData> {
        return discussItemList.filter { it.messageItem != null }
            .map { messageDataMapper(it.messageItem!!) }
    }
}
