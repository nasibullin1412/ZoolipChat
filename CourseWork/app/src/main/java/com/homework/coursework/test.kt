package com.homework.coursework

import com.homework.coursework.data.EmojiData
import com.homework.coursework.data.MessageData
import com.homework.coursework.data.UserData

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