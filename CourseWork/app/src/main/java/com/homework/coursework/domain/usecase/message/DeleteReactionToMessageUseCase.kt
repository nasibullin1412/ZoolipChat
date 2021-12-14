package com.homework.coursework.domain.usecase.message

import com.homework.coursework.domain.entity.EmojiData
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.repository.ReactionRepository
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Used when user want get all streams without any action with search
 */
interface DeleteReactionToMessageUseCase : (MessageData, EmojiData) -> Completable {
    override fun invoke(messageData: MessageData, emojiData: EmojiData): Completable
}

class DeleteReactionToMessageUseCaseImpl @Inject constructor(
    private val repositoryReaction: ReactionRepository
) : DeleteReactionToMessageUseCase {

    override fun invoke(messageData: MessageData, emojiData: EmojiData): Completable {
        return repositoryReaction.deleteReaction(messageData, emojiData)
    }
}
