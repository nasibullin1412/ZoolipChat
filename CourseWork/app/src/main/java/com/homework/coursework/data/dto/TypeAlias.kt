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
@ExperimentalSerializationApi
typealias UserResponse = DtoResponse<UserDto>
@ExperimentalSerializationApi
typealias UserResponseList = DtoResponse<List<UserDto>>
@ExperimentalSerializationApi
typealias SubscribeResponse = DtoResponse<Map<String, List<String>>>
typealias StreamWithTopics = Pair<StreamDto, List<TopicDto>>
typealias UserWithStatus = Pair<UserDto, StatusDto>
