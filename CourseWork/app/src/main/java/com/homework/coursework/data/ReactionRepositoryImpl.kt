package com.homework.coursework.data

import com.homework.coursework.data.frameworks.network.ApiService
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.REACTION_TYPE
import com.homework.coursework.domain.entity.EmojiData
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.repository.ReactionRepository
import io.reactivex.Completable
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class ReactionRepositoryImpl(
    private val apiService: ApiService
) : ReactionRepository {

    override fun addReaction(messageData: MessageData, emojiData: EmojiData): Completable {
        return apiService.addReaction(
            messageId = messageData.messageId,
            emojiName = emojiData.emojiName,
            emojiCode = emojiData.emojiCode,
            reactionType = REACTION_TYPE
        )
    }

    override fun deleteReaction(messageData: MessageData, emojiData: EmojiData): Completable {
        return apiService.deleteReaction(
            messageId = messageData.messageId,
            emojiName = emojiData.emojiName,
            emojiCode = emojiData.emojiCode,
            reactionType = REACTION_TYPE
        )
    }
}
