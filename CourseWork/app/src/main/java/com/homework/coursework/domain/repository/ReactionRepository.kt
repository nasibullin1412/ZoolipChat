package com.homework.coursework.domain.repository

import com.homework.coursework.domain.entity.EmojiData
import com.homework.coursework.domain.entity.MessageData
import io.reactivex.Completable

interface ReactionRepository {
    fun addReaction(messageData: MessageData, emojiData: EmojiData): Completable
    fun deleteReaction(messageData: MessageData, emojiData: EmojiData): Completable
}
