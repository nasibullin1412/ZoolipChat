package com.homework.coursework.presentation.adapter.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopicItem(
    val id: Int,
    val topicName: String,
    val numberOfMess: Int,
    val isEven: Boolean
) : Parcelable
