package com.homework.coursework.domain.usecase

import com.homework.coursework.domain.entity.EmojiData
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.repository.ReactionRepository
import io.reactivex.Completable

/**
 * Used when user want get all streams without any action with search
 */
interface AddReactionToMessageUseCase : (MessageData, EmojiData) -> Completable {
    override fun invoke(messageData: MessageData, emojiData: EmojiData): Completable
}

class AddReactionToMessageUseCaseImpl(
    private val repositoryReaction: ReactionRepository
) : AddReactionToMessageUseCase {

    override fun invoke(messageData: MessageData, emojiData: EmojiData): Completable {
        return repositoryReaction.addReaction(messageData, emojiData)
    }
}
