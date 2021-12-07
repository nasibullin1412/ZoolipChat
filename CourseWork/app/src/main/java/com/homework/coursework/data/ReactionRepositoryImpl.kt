package com.homework.coursework.data

import com.homework.coursework.data.frameworks.network.ApiService
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.REACTION_TYPE
import com.homework.coursework.domain.entity.EmojiData
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.repository.ReactionRepository
import dagger.Lazy
import io.reactivex.Completable
import javax.inject.Inject

class ReactionRepositoryImpl @Inject constructor(
    private val _apiService: Lazy<ApiService>
) : ReactionRepository {

    private val apiService: ApiService get() = _apiService.get()

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
