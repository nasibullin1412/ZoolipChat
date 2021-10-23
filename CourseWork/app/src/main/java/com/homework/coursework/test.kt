package com.homework.coursework

import com.homework.coursework.data.*

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
    ChannelData(
        id = 0,
        channelName = "#general",
        topicList = arrayListOf(
            ChannelTopic(
                id = 0,
                topicName = "#Testing"
            ),
            ChannelTopic(
                id = 1,
                topicName = "#Bruh"
            )
        )
    ),
    ChannelData(
        id = 1,
        channelName = "#Development",
        topicList = arrayListOf(
            ChannelTopic(
                id = 0,
                topicName = "#Testing"
            ),
            ChannelTopic(
                id = 1,
                topicName = "#Bruh"
            )
        )
    ),
    ChannelData(
        id = 2,
        channelName = "#Design",
        topicList = arrayListOf(
            ChannelTopic(
                id = 0,
                topicName = "#Testing"
            ),
            ChannelTopic(
                id = 1,
                topicName = "#Bruh"
            )
        )
    )
)