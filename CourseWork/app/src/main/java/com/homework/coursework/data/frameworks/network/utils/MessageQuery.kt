package com.homework.coursework.data.frameworks.network.utils

import com.homework.coursework.data.dto.Narrow
import com.homework.coursework.domain.entity.EmojiData
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class MessageQuery @Inject constructor() {

    fun getFirstMessages(
        streamData: StreamData,
        topicData: TopicData
    ): Map<String, String> {
        val narrow = Narrow.createNarrowForMessage(streamData, topicData)
        return mapOf(
            "anchor" to NetworkConstants.NEWEST,
            "num_after" to NetworkConstants.NUM_AFTER.toString(),
            "num_before" to NetworkConstants.NUM_BEFORE.toString(),
            "narrow" to narrow
        )
    }

    fun loadMoreMessages(
        streamData: StreamData,
        topicData: TopicData,
        numBefore: Int
    ): Map<String, String> {
        val narrow = Narrow.createNarrowForMessage(streamData, topicData)
        return mapOf(
            "anchor" to topicData.numberOfMess.toString(),
            "num_after" to NetworkConstants.NUM_AFTER.toString(),
            "num_before" to numBefore.toString(),
            "narrow" to narrow
        )
    }

    fun reactionQuery(
        emojiData: EmojiData
    ): Map<String, String> {
        return emojiData.run {
            mapOf(
                "emoji_name" to emojiName,
                "emoji_code" to emojiCode,
                "reaction_type" to NetworkConstants.REACTION_TYPE
            )
        }
    }

    fun addMessage(
        streamData: StreamData,
        content: String,
        topicData: TopicData
    ): Map<String, String> {
        return mapOf(
            "type" to NetworkConstants.STREAM,
            "to" to streamData.streamName,
            "content" to content,
            "topic" to topicData.topicName
        )
    }

    fun editMessage(messageData: MessageData): Map<String, String> = messageData.run {
        mapOf(
            "topic" to topicName,
            "content" to messageContent,
        )
    }
}
