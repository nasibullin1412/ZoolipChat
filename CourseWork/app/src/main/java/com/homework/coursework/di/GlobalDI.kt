package com.homework.coursework.di

import com.homework.coursework.data.MessageRepositoryImpl
import com.homework.coursework.data.ReactionRepositoryImpl
import com.homework.coursework.data.StreamRepositoryImpl
import com.homework.coursework.data.UserRepositoryImpl
import com.homework.coursework.data.frameworks.database.AppDatabase
import com.homework.coursework.data.frameworks.network.ApiService
import com.homework.coursework.domain.repository.MessageRepository
import com.homework.coursework.domain.repository.ReactionRepository
import com.homework.coursework.domain.repository.StreamRepository
import com.homework.coursework.domain.repository.UserRepository
import com.homework.coursework.domain.usecase.*
import com.homework.coursework.presentation.adapter.mapper.StreamItemMapper
import com.homework.coursework.presentation.adapter.mapper.UserItemMapper
import com.homework.coursework.presentation.profile.elm.ProfileActor
import com.homework.coursework.presentation.profile.elm.ProfileStoreFactory
import com.homework.coursework.presentation.stream.elm.StreamActor
import com.homework.coursework.presentation.stream.elm.StreamStoreFactory
import kotlinx.serialization.ExperimentalSerializationApi


@ExperimentalSerializationApi
class GlobalDI private constructor() {

    private val apiService: ApiService by lazy { ApiService.instance }
    private val appDatabase: AppDatabase by lazy { AppDatabase.instance }

    val messageRepository: MessageRepository by lazy {
        MessageRepositoryImpl(
            apiService = apiService,
            messageDao = appDatabase.messageDao(),
            userDao = appDatabase.userDao(),
            messageToUserCrossRefDao = appDatabase.messageToUserCrossRefDao()
        )
    }

    val reactionRepository: ReactionRepository by lazy {
        ReactionRepositoryImpl(apiService = apiService)
    }

    val streamRepository: StreamRepository by lazy {
        StreamRepositoryImpl(
            apiService = apiService,
            streamDao = appDatabase.streamDao()
        )
    }

    val userRepository: UserRepository by lazy {
        UserRepositoryImpl(
            apiService = apiService,
            userDao = appDatabase.userDao()
        )
    }

    val addMessage: AddMessageUseCase by lazy {
        AddMessageUseCaseImpl(
            messageRepository
        )
    }

    val addReaction: AddReactionToMessageUseCase by lazy {
        AddReactionToMessageUseCaseImpl(
            reactionRepository
        )
    }

    val deleteReactionToMessage: DeleteReactionToMessageUseCase by lazy {
        DeleteReactionToMessageUseCaseImpl(reactionRepository)
    }

    val getAllStreams: GetAllStreamsUseCase by lazy {
        GetAllStreamsUseCaseImpl(streamRepository)
    }

    val getMe: GetMeUseCase by lazy {
        GetMeUseCaseImpl(userRepository)
    }

    val getSubscribedStreams: GetSubscribedStreamsUseCase by lazy {
        GetSubscribedStreamsUseCaseImpl(streamRepository)
    }

    val getTopicMessages: GetTopicMessagesUseCase by lazy {
        GetTopicMessagesUseCaseImpl(messageRepository)
    }

    val initMessage: InitMessageUseCase by lazy {
        InitMessageUseCaseImpl(messageRepository)
    }

    val saveMessage: SaveMessageUseCase by lazy {
        SaveMessageUseCaseImpl(messageRepository)
    }

    val searchAllStreams: SearchAllStreamsUseCase by lazy {
        SearchAllStreamsUseCaseImpl(streamRepository)
    }

    val searchSubscribedStreams: SearchSubscribeStreamsUseCase by lazy {
        SearchSubscribeStreamsUseCaseImpl(streamRepository)
    }

    private val userItemMapper: UserItemMapper by lazy { UserItemMapper() }

    private val streamItemMapper: StreamItemMapper by lazy { StreamItemMapper() }

    private val streamActor: StreamActor by lazy {
        StreamActor(
            streamToItemMapper = streamItemMapper,
            getAllStreams = getAllStreams,
            getSubscribedStreams = getSubscribedStreams,
            searchAllStream = searchAllStreams,
            searchSubscribedStreams = searchSubscribedStreams
        )
    }

    private val profileActor by lazy {
        ProfileActor(
            getMe = getMe,
            userItemMapper = userItemMapper
        )
    }

    val profileElmStoreFactory by lazy {
        ProfileStoreFactory(profileActor)
    }

    val subscribedStreamStoreFactory by lazy {
        StreamStoreFactory(streamActor)
    }

    val allStreamStoreFactory by lazy {
        StreamStoreFactory(streamActor)
    }

    companion object {

        lateinit var instance: GlobalDI

        fun init() {
            instance = GlobalDI()
        }
    }
}