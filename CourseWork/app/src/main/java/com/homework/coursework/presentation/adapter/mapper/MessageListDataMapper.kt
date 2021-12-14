package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.presentation.adapter.data.chat.ChatItem
import com.homework.coursework.presentation.adapter.data.chat.MessageItem
import dagger.Reusable
import javax.inject.Inject

@Reusable
class MessageListDataMapper @Inject constructor() : (List<ChatItem>) -> (List<MessageData>) {

    @Inject
    internal lateinit var messageDataMapper: MessageDataMapper

    override fun invoke(chatItemList: List<ChatItem>): List<MessageData> {
        return chatItemList.filterIsInstance<MessageItem>()
            .map { messageDataMapper(it) }
    }
}
