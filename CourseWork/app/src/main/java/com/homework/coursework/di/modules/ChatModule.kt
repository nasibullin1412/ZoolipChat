package com.homework.coursework.di.modules

import com.homework.coursework.di.ChatFragmentScope
import com.homework.coursework.domain.usecase.*
import com.homework.coursework.presentation.adapter.mapper.*
import com.homework.coursework.presentation.chat.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.core.store.Store

@Module
class ChatModule {

    @ChatFragmentScope
    @Provides
    fun provideChatStore(
        chatActor: ChatActor,
        chatProfileReducer: ChatReducer
    ): Store<Event, Effect, State> {
        return ChatStoreFactory(chatActor, chatProfileReducer).provide()
    }

    @ChatFragmentScope
    @Provides
    fun provideChatActor(
        getTopicMessages: GetTopicMessagesUseCase,
        addReaction: AddReactionToMessageUseCase,
        deleteReactionToMessage: DeleteReactionToMessageUseCase,
        addMessage: AddMessageUseCase,
        initMessage: InitMessageUseCase,
        saveMessage: SaveMessageUseCase,
        getCurrentUserId: GetCurrentUserIdUseCase,
        streamDataMapper: StreamDataMapper,
        topicDataMapper: TopicDataMapper,
        discussItemMapper: DiscussItemMapper,
        messageDataMapper: MessageDataMapper,
        messageDataListMapper: MessageListDataMapper,
        emojiDataMapper: EmojiDataMapper
    ): ChatActor {
        return ChatActor(
            getTopicMessages = getTopicMessages,
            addReactionToMessage = addReaction,
            deleteReactionToMessage = deleteReactionToMessage,
            addMessages = addMessage,
            initMessages = initMessage,
            saveMessage = saveMessage,
            getCurrentId = getCurrentUserId,
            streamDataMapper = streamDataMapper,
            topicDataMapper = topicDataMapper,
            discussToItemMapper = discussItemMapper,
            messageDataMapper = messageDataMapper,
            messageListDataMapper = messageDataListMapper,
            emojiDataMapper = emojiDataMapper
        )
    }
}
