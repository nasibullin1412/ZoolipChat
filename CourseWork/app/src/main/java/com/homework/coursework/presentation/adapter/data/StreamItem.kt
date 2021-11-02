package com.homework.coursework.presentation.adapter.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StreamItem(
    val id: Int,
    val streamName: String,
    val description: String,
    val dateCreated: Int,
    var topicItemList: ArrayList<TopicItem>?,
    var isTouched: Boolean
) : Parcelable
