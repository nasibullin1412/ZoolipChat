package com.homework.coursework.presentation.adapter.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopicItem(
    val id: Int,
    val topicName: String,
    val numberOfMess: Int,
    val isEven: Boolean
) : Parcelable{
    companion object{
        const val TOPIC_UNKNOWN_ID = -1

        fun createEmptyTopicItem(): TopicItem{
            return TopicItem(
                id = TOPIC_UNKNOWN_ID,
                topicName = "",
                numberOfMess = -1,
                isEven = false
            )
        }
    }
}
