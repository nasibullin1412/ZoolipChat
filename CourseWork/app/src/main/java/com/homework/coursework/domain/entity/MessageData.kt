package com.homework.coursework.domain.entity

data class MessageData(
    val messageId: Int,
    val userData: UserData,
    val messageContent: String,
    val emojis: List<EmojiData>,
    val date: Long,
    val isCurrentUserMessage: Boolean,
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
            errorHandle = ErrorHandle(isError = true, error = throwable)
        )
    }
}
