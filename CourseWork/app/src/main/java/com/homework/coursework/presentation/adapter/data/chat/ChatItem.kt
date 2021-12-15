package com.homework.coursework.presentation.adapter.data.chat

import com.homework.coursework.presentation.adapter.data.ErrorHandle

sealed class ChatItem(var id: Int = 0, val errorHandle: ErrorHandle = ErrorHandle()) {
    companion object {
        fun getErrorChatItem(error: Throwable): ErrorItem {
            return ErrorItem(errorHandleItem = ErrorHandle(error = error, isError = true))
        }
    }
}

inline fun List<ChatItem>.firstWithMessageItem(condition: (Int) -> (Boolean)): MessageItem {
    return first {
        if (it is MessageItem) {
            condition(it.messageId)
        } else {
            false
        }
    } as MessageItem
}

inline fun <reified T : ChatItem> List<ChatItem>.firstWithInstance(): T? {
    return firstOrNull { it is T } as T?
}

inline fun <reified T : ChatItem> List<ChatItem>.lastWithInstance(): T? {
    return lastOrNull { it is T } as T?
}

inline fun <reified T: ChatItem> List<ChatItem>.dropIfFirst(): List<ChatItem>{
    return if (this.first() is T){
        drop(1)
    } else{
        this
    }
}
