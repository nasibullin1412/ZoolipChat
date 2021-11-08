package com.homework.coursework.data

import com.homework.coursework.data.mappers.MessageDtoMapper
import com.homework.coursework.domain.entity.MessageData
import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.MessageRepository
import com.homework.coursework.presentation.App
import com.homework.coursework.data.dto.Narrow
import com.homework.coursework.presentation.frameworks.network.utils.NetworkConstants.ANCHOR
import com.homework.coursework.presentation.frameworks.network.utils.NetworkConstants.NUM_AFTER
import com.homework.coursework.presentation.frameworks.network.utils.NetworkConstants.NUM_BEFORE
import io.reactivex.Observable
import kotlinx.serialization.ExperimentalSerializationApi


@ExperimentalSerializationApi
class MessageRepositoryImpl : MessageRepository {

    private val messageDtoMapper: MessageDtoMapper = MessageDtoMapper()

    override fun loadMessages(
        streamData: StreamData,
        topicData: TopicData
    ): Observable<List<MessageData>> {
        val narrow = Narrow.createNarrowForMessage(streamData, topicData)
        return App.instance.apiService.getMessages(
            anchor = ANCHOR,
            numAfter = NUM_AFTER,
            numBefore = NUM_BEFORE,
            narrow = narrow
        ).map(messageDtoMapper)
    }
}
/*
    @WorkerThread
    private fun generateMessagesList(idStream: Int, idTopic: Int): List<MessageData> {
        Log.d("Message Moc", Thread.currentThread().name)
        if (Random.nextInt() % 3 == 1) {
            throw IllegalArgumentException("Unexpected idStream")
        }
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
            2 -> {
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
                        messageContent = "Колобок повесился",
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
                        messageContent = "Буратино утонул",
                        emojis = arrayListOf(),
                        date = "2 Фев"
                    ),

                    MessageData(
                        messageId = 2,
                        userData = UserData(
                            0, "Павел Дуров",
                            "https://clck.ru/YEN9d"
                        ),
                        messageContent = "Русалка села на шпагат",
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
}*/
