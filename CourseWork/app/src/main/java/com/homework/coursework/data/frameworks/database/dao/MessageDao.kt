package com.homework.coursework.data.frameworks.database.dao

import androidx.room.*
import com.homework.coursework.data.frameworks.database.entities.EmojiEntity
import com.homework.coursework.data.frameworks.database.entities.MessageEntity
import com.homework.coursework.data.frameworks.database.entities.MessageWithEmojiEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MessageDao {
    @Query(
        "SELECT COUNT(id) FROM message_table " +
                "WHERE stream_id = :streamId AND topic_name = :topicName"
    )
    fun getMessageNumber(streamId: Int, topicName: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmoji(emojiEntity: EmojiEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(messageEntity: MessageEntity)

    @Query(
        "SELECT * FROM message_table WHERE stream_id = :streamId " +
                "AND topic_name = :topicName ORDER BY LOWER(message_id) ASC"
    )
    fun getMessages(streamId: Int, topicName: String): Single<List<MessageWithEmojiEntity>>

    @Transaction
    fun insertOneMessage(messageWithEmojiEntity: MessageWithEmojiEntity) {
        insertMessage(messageWithEmojiEntity.messageEntity)
        for (emoji in messageWithEmojiEntity.emojiEntity) {
            insertEmoji(emoji)
        }
    }

    fun insertMessages(messageWithEmojis: List<MessageWithEmojiEntity>): Completable {
        for (stream in messageWithEmojis) {
            insertOneMessage(stream)
        }
        return Completable.complete()
    }
}
