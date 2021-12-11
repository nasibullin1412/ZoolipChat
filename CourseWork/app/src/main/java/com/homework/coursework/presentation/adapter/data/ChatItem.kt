package com.homework.coursework.presentation.adapter.data

sealed class ChatItem(var id: Int, val errorHandle: ErrorHandle = ErrorHandle()) {
    companion object {
        fun getErrorChatItem(error: Throwable): ErrorItem {
            return ErrorItem(errorHandleItem = ErrorHandle(error = error, isError = true))
        }
    }
}
