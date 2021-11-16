package com.homework.coursework.data.dto

import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
typealias StreamsResponse = DtoResponse<List<StreamDto>>
@ExperimentalSerializationApi
typealias TopicsResponse = DtoResponse<List<TopicDto>>
@ExperimentalSerializationApi
typealias MessagesResponse = DtoResponse<List<MessageDto>>
@ExperimentalSerializationApi
typealias StatusResponse = DtoResponse<StatusDto>
@ExperimentalSerializationApi
typealias AddMessageResponse = DtoResponse<Int>
typealias StreamWithTopics = Pair<StreamDto, List<TopicDto>>
typealias UserWithStatus = Pair<UserDto, StatusDto>
