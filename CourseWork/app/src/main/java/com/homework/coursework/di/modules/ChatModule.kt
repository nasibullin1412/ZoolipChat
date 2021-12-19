package com.homework.coursework.di.modules

import com.homework.coursework.di.ChatFragmentScope
import com.homework.coursework.di.StreamChatStore
import com.homework.coursework.di.TopicChatStore
import com.homework.coursework.domain.usecase.*
import com.homework.coursework.domain.usecase.message.*
import com.homework.coursework.domain.usecase.users.GetCurrentUserIdUseCase
import com.homework.coursework.presentation.adapter.mapper.*
import com.homework.coursework.presentation.ui.chat.DeleteRecycleListMessage
import com.homework.coursework.presentation.ui.chat.UpdateRecycleList
import com.homework.coursework.presentation.ui.chat.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.core.store.Store

@Module
class ChatModule {

    @ChatFragmentScope
    @Provides
    @TopicChatStore
    fun provideTopicChatStore(
        chatActor: ChatActor,
        chatProfileReducer: ChatReducer
    ): Store<Event, Effect, State> {
        return ChatStoreFactory(chatActor, chatProfileReducer).provide()
    }

    @ChatFragmentScope
    @Provides
    fun provideChatActor(
        getMessages: GetMessagesUseCase,
        addReaction: AddReactionToMessageUseCase,
        deleteReactionToMessage: DeleteReactionToMessageUseCase,
        addMessage: AddMessageUseCase,
        initMessage: InitMessageUseCase,
        saveMessage: SaveMessageUseCase,
        getCurrentUserId: GetCurrentUserIdUseCase,
        updateMessageUseCase: UpdateMessageUseCase,
        deleteMessageUseCase: DeleteMessageUseCase,
        addFileUseCase: AddFileUseCase,
        streamDataMapper: StreamDataMapper,
        topicDataMapper: TopicDataMapper,
        chatItemMapper: ChatItemMapper,
        messageDataMapper: MessageDataMapper,
        messageDataListMapper: MessageListDataMapper,
        emojiDataMapper: EmojiDataMapper,
        updateRecycleList: UpdateRecycleList,
        deleteRecycleListMessage: DeleteRecycleListMessage
    ): ChatActor {
        return ChatActor(
            getMessages = getMessages,
            addReactionToMessage = addReaction,
            deleteReactionToMessage = deleteReactionToMessage,
            addMessages = addMessage,
            initMessages = initMessage,
            saveMessage = saveMessage,
            getCurrentId = getCurrentUserId,
            updateMessage = updateMessageUseCase,
            addFile = addFileUseCase,
            streamDataMapper = streamDataMapper,
            topicDataMapper = topicDataMapper,
            chatToItemMapper = chatItemMapper,
            messageDataMapper = messageDataMapper,
            messageListDataMapper = messageDataListMapper,
            emojiDataMapper = emojiDataMapper,
            updateRecycleList = updateRecycleList,
            deleteMessage = deleteMessageUseCase,
            deleteRecycleListMessage = deleteRecycleListMessage
        )
    }

    @ChatFragmentScope
    @Provides
    @StreamChatStore
    fun provideStreamChatStore(
        chatActor: ChatActor,
        chatProfileReducer: ChatReducer
    ): Store<Event, Effect, State> {
        return ChatStoreFactory(chatActor, chatProfileReducer).provide()
    }
}
