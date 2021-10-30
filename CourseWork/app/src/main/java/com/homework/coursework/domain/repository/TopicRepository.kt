package com.homework.coursework.domain.repository

import com.homework.coursework.domain.entity.TopicData

interface TopicRepository {
    fun loadTopics(idChannel: Int): List<TopicData>
}

