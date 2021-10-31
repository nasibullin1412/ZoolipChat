package com.homework.coursework

import com.homework.coursework.domain.entity.*

val testList = arrayListOf(
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

val channelListData = arrayListOf(
    StreamData(
        id = 0,
        streamName = "#general",
        description = "general stream",
        dateCreated = 100
    ),
    StreamData(
        id = 1,
        streamName = "#Development",
        description = "testing stream",
        dateCreated = 100
    ),
    StreamData(
        id = 2,
        streamName = "#Design",
        description = "general stream",
        dateCreated = 100
    )
)
