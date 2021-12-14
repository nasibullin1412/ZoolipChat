package com.homework.coursework.presentation.adapter.mapper

import com.homework.coursework.domain.entity.MessageData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class ChatAddTopicNames @Inject constructor() : (List<MessageData>) -> (ArrayList<Int>) {
    override fun invoke(messageList: List<MessageData>): ArrayList<Int> {
        val indexes: ArrayList<Int> = arrayListOf()
        if (messageList.isEmpty() || messageList.first().errorHandle.isError) {
            return indexes
        }
        indexes.add(messageList.first().messageId)
        for (i in 1..messageList.lastIndex) {
            if (messageList[i - 1].topicName != messageList[i].topicName) {
                indexes.add(messageList[i].messageId)
            }
        }
        return indexes
    }
}
