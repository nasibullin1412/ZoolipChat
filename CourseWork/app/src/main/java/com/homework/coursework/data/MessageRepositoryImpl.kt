package com.homework.coursework.data

import androidx.annotation.WorkerThread
import com.homework.coursework.domain.entity.EmojiData
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.entity.UserData
import com.homework.coursework.domain.repository.MessageRepository

class MessageRepositoryImpl : MessageRepository {
    override fun loadMessages(idStream: Int, idTopic: Int): List<MessageData> {
        return generateAllStreamList(idStream, idTopic)
    }

    @WorkerThread
    private fun generateAllStreamList(idStream: Int, idTopic: Int): List<MessageData> {
        return when (idStream) {
            0 -> {
                generalStream(idTopic)
            }
            1 -> {
                developStream(idTopic)
            }
            2 -> {
                designStream(idTopic)
            }
            else -> throw IllegalArgumentException("Unexpected idStream")
        }
    }

    private fun designStream(idTopic: Int): List<MessageData> {
        return when (idTopic) {
            0 -> {
                arrayListOf(
                    MessageData(
                        messageId = 0,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Маму люби",
                        emojis = arrayListOf(
                            EmojiData(
                                emojiCode = "😗",
                                emojiNumber = 1,
                                isCurrUserReacted = false
                            )
                        ),
                        date = "1 Фев"
                    ),
                    MessageData(
                        messageId = 1,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Суп посоли",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    ),

                    MessageData(
                        messageId = 2,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Ковёр оттряни",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    ),

                    MessageData(
                        messageId = 3,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Мухамед Али",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    )
                )
            }
            1 -> {
                arrayListOf(
                    MessageData(
                        messageId = 0,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Маму люби",
                        emojis = arrayListOf(
                            EmojiData(
                                emojiCode = "😗",
                                emojiNumber = 1,
                                isCurrUserReacted = false
                            )
                        ),
                        date = "1 Фев"
                    ),
                    MessageData(
                        messageId = 1,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Суп посоли",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    ),

                    MessageData(
                        messageId = 2,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Ковёр оттряни",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    ),

                    MessageData(
                        messageId = 3,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Мухамед Али",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    )
                )
            }
            else -> throw IllegalArgumentException("Unexpected idStream")
        }
    }

    private fun developStream(idTopic: Int): List<MessageData> {
        return when (idTopic) {
            0 -> {
                arrayListOf(
                    MessageData(
                        messageId = 0,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Маму люби",
                        emojis = arrayListOf(
                            EmojiData(
                                emojiCode = "😗",
                                emojiNumber = 1,
                                isCurrUserReacted = false
                            )
                        ),
                        date = "1 Фев"
                    ),
                    MessageData(
                        messageId = 1,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Суп посоли",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    ),

                    MessageData(
                        messageId = 2,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Ковёр оттряни",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    ),

                    MessageData(
                        messageId = 3,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Мухамед Али",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    )
                )
            }
            1 -> {
                arrayListOf(
                    MessageData(
                        messageId = 0,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Маму люби",
                        emojis = arrayListOf(
                            EmojiData(
                                emojiCode = "😗",
                                emojiNumber = 1,
                                isCurrUserReacted = false
                            )
                        ),
                        date = "1 Фев"
                    ),
                    MessageData(
                        messageId = 1,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Суп посоли",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    ),

                    MessageData(
                        messageId = 2,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Ковёр оттряни",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    ),

                    MessageData(
                        messageId = 3,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Мухамед Али",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    )
                )
            }
            else -> throw IllegalArgumentException("Unexpected idStream")
        }
    }

    private fun generalStream(idTopic: Int): List<MessageData> {
        return when (idTopic) {
            0 -> {
                arrayListOf(
                    MessageData(
                        messageId = 0,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Маму люби",
                        emojis = arrayListOf(
                            EmojiData(
                                emojiCode = "😗",
                                emojiNumber = 1,
                                isCurrUserReacted = false
                            )
                        ),
                        date = "1 Фев"
                    ),
                    MessageData(
                        messageId = 1,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Суп посоли",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    ),

                    MessageData(
                        messageId = 2,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Ковёр оттряни",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    ),

                    MessageData(
                        messageId = 3,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Мухамед Али",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    )
                )
            }
            1 -> {
                arrayListOf(
                    MessageData(
                        messageId = 0,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Маму люби",
                        emojis = arrayListOf(
                            EmojiData(
                                emojiCode = "😗",
                                emojiNumber = 1,
                                isCurrUserReacted = false
                            )
                        ),
                        date = "1 Фев"
                    ),
                    MessageData(
                        messageId = 1,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Суп посоли",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    ),

                    MessageData(
                        messageId = 2,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Ковёр оттряни",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    ),

                    MessageData(
                        messageId = 3,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Мухамед Али",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    )
                )
            }
            else -> throw IllegalArgumentException("Unexpected idStream")
        }
    }
}