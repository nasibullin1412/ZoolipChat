package com.homework.coursework.presentation.ui.chat.elm

import com.homework.coursework.domain.usecase.message.*
import com.homework.coursework.domain.usecase.users.GetCurrentUserIdUseCase
import com.homework.coursework.presentation.adapter.mapper.*
import com.homework.coursework.presentation.ui.chat.UpdateRecycleList
import io.reactivex.Observable
import vivid.money.elmslie.core.ActorCompat

class ChatActor(
    private val getMessages: GetMessagesUseCase,
    private val addReactionToMessage: AddReactionToMessageUseCase,
    private val deleteReactionToMessage: DeleteReactionToMessageUseCase,
    private val addMessages: AddMessageUseCase,
    private val initMessages: InitMessageUseCase,
    private val saveMessage: SaveMessageUseCase,
    private val getCurrentId: GetCurrentUserIdUseCase,
    private val updateMessage: UpdateMessageUseCase,
    private val streamDataMapper: StreamDataMapper,
    private val topicDataMapper: TopicDataMapper,
    private val chatToItemMapper: ChatItemMapper,
    private val messageDataMapper: MessageDataMapper,
    private val messageListDataMapper: MessageListDataMapper,
    private val emojiDataMapper: EmojiDataMapper,
    private val updateRecycleList: UpdateRecycleList
) : ActorCompat<Command, Event> {

    override fun execute(command: Command): Observable<Event> = when (command) {
        is Command.AddReaction -> {
            addReactionToMessage(
                messageData = messageDataMapper(command.messageItem),
                emojiData = emojiDataMapper(command.emojiItem),
            ).mapEvents(Event.Internal.ReactionChanged) { error ->
                Event.Internal.ErrorCommands(error)
            }
        }
        is Command.DeleteReaction -> {
            deleteReactionToMessage(
                messageData = messageDataMapper(command.messageItem),
                emojiData = emojiDataMapper(command.emojiItem),
            ).mapEvents(Event.Internal.ReactionChanged) { error ->
                Event.Internal.ErrorCommands(error)
            }
        }
        is Command.LoadFirstPage -> {
            initMessages(
                streamData = streamDataMapper(command.streamItem),
                topicData = topicDataMapper(command.topicItem),
                currId = command.currId
            ).map {
                chatToItemMapper(
                    messageDataList = it,
                    currId = command.currId,
                    isUpdate = false
                )
            }.mapEvents(
                    { list -> Event.Internal.InitPageLoaded(list) },
                    { error -> Event.Internal.ErrorLoading(error) }
                )
        }
        is Command.LoadMessages -> {
            getMessages(
                streamData = streamDataMapper(command.streamItem),
                topicData = topicDataMapper(command.topicItem),
                currId = command.currId
            ).map {
                chatToItemMapper(
                    messageDataList = it,
                    currId = command.currId,
                    isUpdate = false
                )
            }.mapEvents(
                    { list -> Event.Internal.PageLoaded(list) },
                    { error -> Event.Internal.ErrorCommands(error) }
                )
        }
        is Command.SendMessage -> {
            addMessages(
                streamData = streamDataMapper(command.streamItem),
                topicData = topicDataMapper(command.topicItem),
                content = command.content
            ).mapEvents(
                { messageId -> Event.Internal.MessageAdded(messageId) },
                { error -> Event.Internal.ErrorLoading(error) }
            )
        }
        is Command.MergeWithNewMessageList -> {
            updateRecycleList(oldList = command.oldList, newList = command.newList)
                .mapEvents(
                    { list -> Event.Internal.UpdateRecycle(list) },
                    { error -> Event.Internal.ErrorLoading(error) }
                )
        }
        is Command.SaveMessage -> {
            saveMessage(
                streamData = streamDataMapper(command.streamItem),
                topicData = topicDataMapper(command.topicItem),
                messageDataList = messageListDataMapper(command.messages),
                currId = command.currId
            ).mapEvents(Event.Internal.MessagesSaved) {
                Event.Internal.ErrorCommands(it)
            }
        }
        Command.GetCurrentId -> {
            getCurrentId()
                .mapEvents(
                    { item -> Event.Internal.SuccessGetId(item) },
                    { error -> Event.Internal.ErrorLoading(error) }
                )
        }
        is Command.UpdateMessage -> {
            updateMessage(
                streamData = streamDataMapper(command.streamItem),
                topicData = topicDataMapper(command.topicItem),
                currId = command.currId
            ).map {
                chatToItemMapper(
                    messageDataList = it,
                    currId = command.currId,
                    isUpdate = true
                )
            }.mapEvents(
                { item -> Event.Internal.PageLoaded(item) },
                { error -> Event.Internal.ErrorLoading(error) }
            )
        }
    }
}
