package com.homework.coursework.data

import com.homework.coursework.domain.entity.EmojiData
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.repository.ReactionRepository
import com.homework.coursework.presentation.App
import com.homework.coursework.presentation.frameworks.network.utils.NetworkConstants.REACTION_TYPE
import io.reactivex.Completable
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class ReactionRepositoryImpl : ReactionRepository {

    override fun addReaction(messageData: MessageData, emojiData: EmojiData): Completable {
        return App.instance.apiService.addReaction(
            messageId = messageData.messageId,
            emojiName = emojiData.emojiName,
            emojiCode = emojiData.emojiCode,
            reactionType = REACTION_TYPE
        )
    }

    override fun deleteReaction(messageData: MessageData, emojiData: EmojiData): Completable {
        return App.instance.apiService.deleteReaction(
            messageId = messageData.messageId,
            emojiName = emojiData.emojiName,
            emojiCode = emojiData.emojiCode,
            reactionType = REACTION_TYPE
        )
    }
}
