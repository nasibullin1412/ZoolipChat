package com.homework.coursework.domain.repository

import com.homework.coursework.domain.entity.StreamData
import io.reactivex.Single

interface StreamRepository {
    fun loadStreams(): Single<List<StreamData>>
    fun loadSubscribedStreams(): Single<List<StreamData>>
}
