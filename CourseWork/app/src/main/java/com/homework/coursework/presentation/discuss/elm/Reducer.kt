package com.homework.coursework.presentation.discuss.elm

import com.homework.coursework.presentation.interfaces.TwoSourceHandleReducer
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class Reducer : DslReducer<Event, State, Effect, Command>(),
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
                    topicItem = event.topicItem
                )
            }
        }
        is Event.Ui.LoadNextPage -> {
            commands {
                +Command.LoadNextPage(
                    streamItem = event.streamItem,
                    topicItem = event.topicItem
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
                    messages = event.messages
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
