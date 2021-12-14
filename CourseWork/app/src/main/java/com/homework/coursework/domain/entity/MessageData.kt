package com.homework.coursework.domain.entity

class MessageData(
    val messageId: Int,
    val userData: UserData,
    val messageContent: String,
    val emojis: List<EmojiData>,
    val date: Long,
    val isCurrentUserMessage: Boolean,
    val topicName: String,
    val errorHandle: ErrorHandle = ErrorHandle()
) {
    companion object {
        fun getErrorObject(throwable: Throwable) = MessageData(
            messageId = 0,
            userData = UserData.getErrorObject(UnknownError()),
            messageContent = "",
            emojis = emptyList(),
            date = 0,
            isCurrentUserMessage = false,
            topicName = "",
            errorHandle = ErrorHandle(isError = true, error = throwable)
        )
    }
}
