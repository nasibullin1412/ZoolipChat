package com.homework.coursework.presentation.ui.chat.elm

import com.homework.coursework.domain.usecase.message.*
import com.homework.coursework.domain.usecase.users.GetCurrentUserIdUseCase
import com.homework.coursework.presentation.adapter.mapper.*
import com.homework.coursework.presentation.ui.chat.DeleteRecycleListMessage
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
    private val deleteMessage: DeleteMessageUseCase,
    private val addFile: AddFileUseCase,
    private val streamDataMapper: StreamDataMapper,
    private val topicDataMapper: TopicDataMapper,
    private val chatToItemMapper: ChatItemMapper,
    private val messageDataMapper: MessageDataMapper,
    private val messageListDataMapper: MessageListDataMapper,
    private val emojiDataMapper: EmojiDataMapper,
    private val updateRecycleList: UpdateRecycleList,
    private val deleteRecycleListMessage: DeleteRecycleListMessage
) : ActorCompat<Command, Event> {

    override fun execute(command: Command): Observable<Event> = when (command) {
        is Command.AddReaction -> with(command) {
            addReactionToMessage(
                messageData = messageDataMapper(messageItem),
                emojiData = emojiDataMapper(emojiItem),
            ).mapEvents(Event.Internal.ReactionChanged) { error ->
                Event.Internal.ErrorCommands(error)
            }
        }
        is Command.DeleteReaction -> with(command) {
            deleteReactionToMessage(
                messageData = messageDataMapper(messageItem),
                emojiData = emojiDataMapper(emojiItem),
            ).mapEvents(Event.Internal.ReactionChanged) { error ->
                Event.Internal.ErrorCommands(error)
            }
        }
        is Command.LoadFirstPage -> with(command) {
            initMessages(
                streamData = streamDataMapper(streamItem),
                topicData = topicDataMapper(topicItem),
                currId = currId
            ).map {
                chatToItemMapper(
                    messageDataList = it,
                    currId = currId,
                    isUpdate = false
                )
            }.mapEvents(
                { list -> Event.Internal.InitPageLoaded(list) },
                { error -> Event.Internal.ErrorLoading(error) }
            )
        }
        is Command.LoadMessages -> with(command) {
            getMessages(
                streamData = streamDataMapper(streamItem),
                topicData = topicDataMapper(topicItem),
                currId = currId
            ).map {
                chatToItemMapper(
                    messageDataList = it,
                    currId = currId,
                    isUpdate = false
                )
            }.mapEvents(
                { list -> Event.Internal.PageLoaded(list) },
                { error -> Event.Internal.ErrorCommands(error) }
            )
        }
        is Command.SendMessage -> with(command) {
            addMessages(
                streamData = streamDataMapper(streamItem),
                topicData = topicDataMapper(topicItem),
                content = content
            ).mapEvents(
                { messageId -> Event.Internal.MessageAdded(messageId) },
                { error -> Event.Internal.ErrorLoading(error) }
            )
        }
        is Command.MergeWithNewMessageList -> with(command) {
            updateRecycleList(oldList = oldList, newList = newList)
                .mapEvents(
                    { list -> Event.Internal.UpdateRecycle(list) },
                    { error -> Event.Internal.ErrorLoading(error) }
                )
        }
        is Command.SaveMessage -> with(command) {
            saveMessage(
                streamData = streamDataMapper(streamItem),
                topicData = topicDataMapper(topicItem),
                messageDataList = messageListDataMapper(messages),
                currId = currId
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
        is Command.UpdateMessage -> with(command) {
            updateMessage(
                streamData = streamDataMapper(streamItem),
                topicData = topicDataMapper(topicItem),
                currId = currId
            ).map {
                chatToItemMapper(
                    messageDataList = it,
                    currId = currId,
                    isUpdate = true
                )
            }.mapEvents(
                { item -> Event.Internal.PageLoaded(item) },
                { error -> Event.Internal.ErrorLoading(error) }
            )
        }
        is Command.DeleteMessage -> {
            deleteMessage(messageDataMapper(command.messageItem))
                .mapEvents(Event.Internal.MessageDeleted) { error ->
                    Event.Internal.ErrorLoading(error)
                }
        }
        is Command.DeleteMessageFromRecycle -> with(command) {
            deleteRecycleListMessage(oldList, deleteId)
                .mapEvents(
                    { list -> Event.Internal.UpdateRecycle(list) },
                    { error -> Event.Internal.ErrorLoading(error) }
                )
        }
        is Command.AddFile -> with(command) {
            addFile(
                stream = streamDataMapper(streamItem),
                topic = topicDataMapper(topicItem),
                input = input,
                fileName = fileName
            ).mapEvents(
                { messageId -> Event.Internal.MessageAdded(messageId) },
                { error -> Event.Internal.ErrorLoading(error) }
            )
        }
    }
}
