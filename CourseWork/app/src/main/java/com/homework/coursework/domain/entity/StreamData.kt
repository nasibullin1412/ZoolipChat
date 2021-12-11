package com.homework.coursework.domain.entity

class StreamData(
    val id: Int,
    val streamName: String,
    val description: String,
    val dateCreated: Int,
    var topics: List<TopicData>,
    var errorHandle: ErrorHandle = ErrorHandle()
) {
    companion object {
        fun getErrorObject(throwable: Throwable) = StreamData(
            id = 0,
            streamName = "",
            description = "",
            dateCreated = 0,
            topics = emptyList(),
            errorHandle = ErrorHandle(isError = true, error = throwable)
        )
    }
}
