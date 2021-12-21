package com.homework.coursework.presentation.ui.chat.elm

import com.homework.coursework.presentation.adapter.data.chat.ChatItem
import com.homework.coursework.presentation.interfaces.TwoSourceHandleReducer
import dagger.Reusable
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject

@Reusable
class ChatReducer @Inject constructor() : DslReducer<Event, State, Effect, Command>(),
    TwoSourceHandleReducer<Event.Internal.InitPageLoaded, State> {

    override fun Result.reduce(event: Event) = when (event) {
        is Event.Internal.ErrorLoading -> errorLoading(event.error)
        is Event.Internal.ErrorCommands -> effects { +Effect.ErrorCommands(event.error) }
        is Event.Internal.InitPageLoaded -> initPageLoaded(event)
        is Event.Internal.PageLoaded -> {
            effects { +Effect.PageLoaded(itemList = event.itemList) }
        }
        is Event.Internal.MessageAdded -> {
            effects { +Effect.MessageAdded(id = event.id) }
        }
        Event.Internal.ReactionChanged -> {
            effects { +Effect.ReactionChanged }
        }
        is Event.Internal.UpdateRecycle -> updateRecycle(event.itemList)
        Event.Internal.MessagesSaved -> {
            effects { +Effect.MessagesSaved }
        }
        is Event.Internal.SuccessGetId -> {
            effects { +Effect.SuccessGetId(event.currId) }
        }
        Event.Internal.MessageDeleted -> {
            effects { +Effect.DeleteMessage }
        }
        is Event.Ui.AddReaction -> addReaction(event)
        is Event.Ui.LoadFirstPage -> loadFirstPage(event)
        Event.Ui.GetCurrentId -> {
            commands { +Command.GetCurrentId }
        }
        is Event.Ui.DeleteReaction -> deleteReaction(event)
        is Event.Ui.LoadNextPage -> loadNextPage(event)
        is Event.Ui.SendMessage -> sendMessage(event)
        is Event.Ui.MergeOldList -> mergeOldList(event)
        is Event.Ui.SaveMessage -> saveMessage(event)
        is Event.Ui.UpdateMessage -> updateMessage(event)
        is Event.Ui.DeleteMessage -> {
            commands { +Command.DeleteMessage(event.messageItem) }
        }
        is Event.Ui.DeleteMessageFromRecycle -> deleteMessageFromRecycle(event)
        is Event.Ui.AddFile -> addFile(event)
    }

    override var isSecondError: Boolean = false

    override fun handleResult(event: Event.Internal.InitPageLoaded): State? {
        return if (isSecondError) {
            State(
                isError = isSecondErrorChange(),
                error = event.itemList.first().errorHandle.error
            )
        } else {
            isSecondErrorChange()
            null
        }
    }

    override fun isWithError(event: Event.Internal.InitPageLoaded) =
        event.itemList.isNotEmpty() && event.itemList.first().errorHandle.isError

    private fun Result.errorLoading(error: Throwable) {
        state {
            copy(
                error = error,
                isLoading = false,
                isError = true,
                isUpdate = false,
                isFirstLoad = false
            )
        }
        effects { +Effect.NextPageLoadError(error) }
    }

    private fun Result.initPageLoaded(event: Event.Internal.InitPageLoaded) {
        if (isWithError(event)) {
            handleResult(event)?.let { state { it } }
        } else {
            state {
                copy(
                    itemList = event.itemList,
                    isLoading = false,
                    isError = false,
                    isUpdate = false,
                    isFirstLoad = true
                )
            }
        }
    }

    private fun Result.updateRecycle(itemList: List<ChatItem>) {
        state {
            copy(
                itemList = itemList,
                isLoading = false,
                isError = false,
                isUpdate = true,
                isFirstLoad = false
            )
        }
    }

    private fun Result.loadFirstPage(event: Event.Ui.LoadFirstPage) = with(event) {
        isSecondError = false
        state {
            copy(
                isLoading = true,
                isError = false,
                isUpdate = false,
                isFirstLoad = false
            )
        }
        commands {
            +Command.LoadFirstPage(
                streamItem = streamItem,
                topicItem = topicItem,
                currId = currId
            )
        }
    }

    private fun Result.addReaction(event: Event.Ui.AddReaction) = with(event) {
        commands {
            +Command.AddReaction(
                messageItem = messageItem,
                emojiItem = emojiItem
            )
        }
    }

    private fun Result.deleteReaction(event: Event.Ui.DeleteReaction) = with(event) {
        commands {
            +Command.DeleteReaction(
                messageItem = messageItem,
                emojiItem = emojiItem
            )
        }
    }

    private fun Result.loadNextPage(event: Event.Ui.LoadNextPage) = with(event) {
        commands {
            +Command.LoadMessages(
                streamItem = streamItem,
                topicItem = topicItem,
                currId = currId
            )
        }
    }

    private fun Result.sendMessage(event: Event.Ui.SendMessage) = with(event) {
        commands {
            +Command.SendMessage(
                streamItem = streamItem,
                topicItem = topicItem,
                content = content
            )
        }
    }

    private fun Result.mergeOldList(event: Event.Ui.MergeOldList) = with(event) {
        commands {
            +Command.MergeWithNewMessageList(
                oldList = oldList,
                newList = newList
            )
        }
    }

    private fun Result.saveMessage(event: Event.Ui.SaveMessage) = with(event) {
        isSecondError = false
        commands {
            +Command.SaveMessage(
                streamItem = streamItem,
                topicItem = topicItem,
                messages = messages,
                currId = currId
            )
        }
    }

    private fun Result.updateMessage(event: Event.Ui.UpdateMessage) = with(event) {
        commands {
            +Command.UpdateMessage(
                streamItem = streamItem,
                topicItem = topicItem,
                currId = currId
            )
        }
    }

    private fun Result.deleteMessageFromRecycle(event: Event.Ui.DeleteMessageFromRecycle) =
        with(event) {
            commands {
                +Command.DeleteMessageFromRecycle(
                    oldList = oldList,
                    deleteId = messageId
                )
            }
        }

    private fun Result.addFile(event: Event.Ui.AddFile) = with(event) {
        commands {
            +Command.AddFile(
                streamItem = streamItem,
                topicItem = topicItem,
                input = input,
                fileName = fileName
            )
        }
    }
}
