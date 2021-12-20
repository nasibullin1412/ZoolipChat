package com.homework.coursework.presentation.viewholder.chat

enum class ChatViewType(val value: Int) {
    MESSAGE_TO(0),
    MESSAGE_FROM(1),
    DATE(2),
    TOPIC_NAME(3);

    companion object {
        fun getChatViewTypeByInt(value: Int) = values().first { it.value == value }
    }
}
