package com.homework.coursework.domain.repository

import com.homework.coursework.domain.entity.SubscribeData
import io.reactivex.Observable

interface SubscribeStreamRepository {
    fun subscribeStream(subscribeData: SubscribeData): Observable<String>
}
