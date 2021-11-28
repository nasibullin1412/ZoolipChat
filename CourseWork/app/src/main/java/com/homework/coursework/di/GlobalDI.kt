package com.homework.coursework.di

import com.homework.coursework.domain.usecase.*
import com.homework.coursework.presentation.App
import com.homework.coursework.presentation.adapter.mapper.*
import com.homework.coursework.presentation.discuss.elm.ChatActor
import com.homework.coursework.presentation.discuss.elm.ChatStoreFactory
import com.homework.coursework.presentation.profile.elm.ProfileActor
import com.homework.coursework.presentation.profile.elm.ProfileStoreFactory
import com.homework.coursework.presentation.stream.elm.StreamActor
import com.homework.coursework.presentation.stream.elm.StreamStoreFactory
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class GlobalDI private constructor() {

    @Inject
    lateinit var addMessage: AddMessageUseCase

    @Inject
    lateinit var addReaction: AddReactionToMessageUseCase

    @Inject
    lateinit var deleteReactionToMessage: DeleteReactionToMessageUseCase

    @Inject
    lateinit var getTopicMessages: GetTopicMessagesUseCase

    @Inject
    lateinit var initMessage: InitMessageUseCase

    @Inject
    lateinit var saveMessage: SaveMessageUseCase

    private val streamDataMapper: StreamDataMapper by lazy { StreamDataMapper() }

    private val topicDataMapper: TopicDataMapper by lazy { TopicDataMapper() }

    private val discussItemMapper: DiscussItemMapper by lazy { DiscussItemMapper() }

    private val messageDataMapper: MessageDataMapper by lazy { MessageDataMapper() }

    private val messageDataListMapper: MessageListDataMapper by lazy { MessageListDataMapper() }

    private val emojiDataMapper: EmojiDataMapper by lazy { EmojiDataMapper() }


    private val discussActor by lazy {
        ChatActor(
            getTopicMessages = getTopicMessages,
            addReactionToMessage = addReaction,
            deleteReactionToMessage = deleteReactionToMessage,
            addMessages = addMessage,
            initMessages = initMessage,
            saveMessage = saveMessage,
            streamDataMapper = streamDataMapper,
            topicDataMapper = topicDataMapper,
            discussToItemMapper = discussItemMapper,
            messageDataMapper = messageDataMapper,
            messageListDataMapper = messageDataListMapper,
            emojiDataMapper = emojiDataMapper
        )
    }


    val topicChatStoreFactory by lazy {
        ChatStoreFactory(discussActor)
    }

    companion object {


        lateinit var instance: GlobalDI

        fun init() {
            instance = GlobalDI()
            App.appComponent.inject(instance)
        }
    }
}
