package com.homework.coursework.data.mappers

import com.homework.coursework.domain.entity.StreamData

interface StreamMapper<T>: (T) -> (List<StreamData>){
    override fun invoke(streamsWithTopics: T): List<StreamData>
}