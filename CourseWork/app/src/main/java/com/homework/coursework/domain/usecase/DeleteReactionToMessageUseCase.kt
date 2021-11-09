package com.homework.coursework.domain.usecase

import com.homework.coursework.data.ReactionRepositoryImpl
import com.homework.coursework.domain.entity.EmojiData
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.repository.ReactionRepository
import io.reactivex.Completable

/**
 * Used when user want get all streams without any action with search
 */
interface DeleteReactionToMessageUseCase : (MessageData, EmojiData) -> Completable {
    override fun invoke(messageData: MessageData, emojiData: EmojiData): Completable
}

class DeleteReactionToMessageUseCaseImpl : DeleteReactionToMessageUseCase {

    private val repositoryReaction: ReactionRepository = ReactionRepositoryImpl()

    override fun invoke(messageData: MessageData, emojiData: EmojiData): Completable {
        return repositoryReaction.deleteReaction(messageData, emojiData)
    }
}
