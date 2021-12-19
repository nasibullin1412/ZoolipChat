package com.homework.coursework.presentation.ui.chat.elm

import com.homework.coursework.presentation.interfaces.TwoSourceHandleReducer
import dagger.Reusable
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject

@Reusable
class ChatReducer @Inject constructor() : DslReducer<Event, State, Effect, Command>(),
    TwoSourceHandleReducer<Event.Internal.InitPageLoaded, State> {

    override fun Result.reduce(event: Event) = when (event) {
        is Event.Internal.ErrorLoading -> {
            state {
                copy(
                    error = event.error,
                    isLoading = false,
                    isError = true,
                    isUpdate = false
                )
            }
            effects { +Effect.NextPageLoadError(event.error) }
        }

        is Event.Internal.ErrorCommands -> {
            effects { +Effect.ErrorCommands(event.error) }
        }

        is Event.Internal.InitPageLoaded -> {
            if (isWithError(event)) {
                handleResult(event)?.let { state { it } }
            } else {
                state {
                    copy(
                        itemList = event.itemList,
                        isLoading = false,
                        isError = false,
                        isUpdate = true
                    )
                }
            }
        }

        is Event.Internal.PageLoaded -> {
            effects { +Effect.PageLoaded(itemList = event.itemList) }
        }

        is Event.Internal.MessageAdded -> {
            effects { +Effect.MessageAdded(id = event.id) }
        }

        Event.Internal.ReactionChanged -> {
            effects { +Effect.ReactionChanged }
        }

        is Event.Internal.UpdateRecycle -> {
            state {
                copy(
                    itemList = event.itemList,
                    isLoading = false,
                    isError = false,
                    isUpdate = true
                )
            }
        }
        Event.Internal.MessagesSaved -> {
            effects { +Effect.MessagesSaved }
        }
        is Event.Internal.SuccessGetId -> {
            effects { +Effect.SuccessGetId(event.currId) }
        }
        Event.Internal.MessageDeleted -> {
            effects { +Effect.DeleteMessage }
        }
        is Event.Ui.AddReaction -> {
            commands {
                +Command.AddReaction(
                    messageItem = event.messageItem,
                    emojiItem = event.emojiItem
                )
            }
        }
        is Event.Ui.DeleteReaction -> {
            commands {
                +Command.DeleteReaction(
                    messageItem = event.messageItem,
                    emojiItem = event.emojiItem
                )
            }
        }
        is Event.Ui.LoadFirstPage -> {
            isSecondError = false
            state {
                copy(
                    isLoading = true,
                    isError = false,
                    isUpdate = false
                )
            }
            commands {
                +Command.LoadFirstPage(
                    streamItem = event.streamItem,
                    topicItem = event.topicItem,
                    currId = event.currId
                )
            }
        }
        is Event.Ui.LoadNextPage -> {
            commands {
                +Command.LoadMessages(
                    streamItem = event.streamItem,
                    topicItem = event.topicItem,
                    currId = event.currId
                )
            }
        }
        is Event.Ui.SendMessage -> {
            commands {
                +Command.SendMessage(
                    streamItem = event.streamItem,
                    topicItem = event.topicItem,
                    content = event.content
                )
            }
        }
        is Event.Ui.MergeOldList -> {
            commands {
                +Command.MergeWithNewMessageList(
                    oldList = event.oldList,
                    newList = event.newList
                )
            }
        }
        is Event.Ui.SaveMessage -> {
            isSecondError = false
            commands {
                +Command.SaveMessage(
                    streamItem = event.streamItem,
                    topicItem = event.topicItem,
                    messages = event.messages,
                    currId = event.currId
                )
            }
        }
        Event.Ui.GetCurrentId -> {
            commands { +Command.GetCurrentId }
        }
        is Event.Ui.UpdateMessage -> {
            commands {
                +Command.UpdateMessage(
                    streamItem = event.streamItem,
                    topicItem = event.topicItem,
                    currId = event.currId
                )
            }
        }
        is Event.Ui.DeleteMessage -> {
            commands { +Command.DeleteMessage(event.messageItem) }
        }
        is Event.Ui.DeleteMessageFromRecycle -> {
            commands {
                +Command.DeleteMessageFromRecycle(
                    oldList = event.oldList,
                    deleteId = event.messageId
                )
            }
        }
        is Event.Ui.AddFile -> {
            commands {
                +Command.AddFile(
                    streamItem = event.streamItem,
                    topicItem = event.topicItem,
                    input = event.input,
                    fileName = event.fileName
                )
            }
        }
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
}
