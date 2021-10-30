package com.homework.coursework.domain.repository

import com.homework.coursework.domain.entity.StreamData

interface StreamRepository {
    fun loadStreams(): List<StreamData>
    fun loadSubscribedStreams(): List<StreamData>
}
