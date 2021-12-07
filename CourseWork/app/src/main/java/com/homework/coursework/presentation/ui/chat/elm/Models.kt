package com.homework.coursework.presentation.ui.chat.elm

import android.os.Parcelable
import com.homework.coursework.presentation.adapter.data.*
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class State(
    val itemList: @RawValue List<DiscussItem> = emptyList(),
    val error: Throwable? = null,
    val messageId: Int = 0,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isUpdate: Boolean = false
) : Parcelable

sealed class Event {

    sealed class Ui : Event() {
        data class LoadFirstPage(
            val streamItem: StreamItem,
            val topicItem: TopicItem,
            val currId: Int
        ) : Ui()

        data class LoadNextPage(
            val streamItem: StreamItem,
            val topicItem: TopicItem,
            val currId: Int
        ) : Ui()

        data class SendMessage(
            val streamItem: StreamItem,
            val topicItem: TopicItem,
            val content: String
        ) : Ui()

        data class AddReaction(val messageItem: MessageItem, val emojiItem: EmojiItem) : Ui()

        data class DeleteReaction(val messageItem: MessageItem, val emojiItem: EmojiItem) : Ui()

        object GetCurrentId : Ui()

        data class MergeOldList(
            val oldList: List<DiscussItem>,
            val newList: List<DiscussItem>
        ) : Ui()

        data class SaveMessage(
            val streamItem: StreamItem,
            val topicItem: TopicItem,
            val messages: List<DiscussItem>,
            val currId: Int
        ) : Ui()
    }

    sealed class Internal : Event() {

        data class InitPageLoaded(val itemList: List<DiscussItem>) : Internal()

        data class PageLoaded(val itemList: List<DiscussItem>) : Internal()

        data class MessageAdded(val id: Int) : Internal()

        data class UpdateRecycle(val itemList: List<DiscussItem>) : Internal()

        object ReactionChanged : Internal()

        object MessagesSaved : Internal()

        data class ErrorCommands(val error: Throwable) : Internal()

        data class ErrorLoading(val error: Throwable) : Internal()

        data class SuccessGetId(val currId: Int) : Internal()
    }
}

sealed class Effect {
    data class NextPageLoadError(val error: Throwable) : Effect()

    data class PageLoaded(val itemList: List<DiscussItem>) : Effect()

    data class MessageAdded(val id: Int) : Effect()

    object ReactionChanged : Effect()

    object MessagesSaved : Effect()

    data class ErrorCommands(val error: Throwable) : Effect()

    data class SuccessGetId(val currId: Int) : Effect()
}

sealed class Command {

    data class LoadFirstPage(
        val streamItem: StreamItem,
        val topicItem: TopicItem,
        val currId: Int
    ) : Command()

    data class LoadNextPage(
        val streamItem: StreamItem,
        val topicItem: TopicItem,
        val currId: Int
    ) : Command()

    data class SendMessage(
        val streamItem: StreamItem,
        val topicItem: TopicItem,
        val content: String
    ) : Command()

    data class AddReaction(val messageItem: MessageItem, val emojiItem: EmojiItem) : Command()

    data class DeleteReaction(val messageItem: MessageItem, val emojiItem: EmojiItem) : Command()

    data class MergeWithNewMessageList(
        val oldList: List<DiscussItem>,
        val newList: List<DiscussItem>
    ) : Command()

    data class SaveMessage(
        val streamItem: StreamItem,
        val topicItem: TopicItem,
        val messages: List<DiscussItem>,
        val currId: Int
    ) : Command()

    object GetCurrentId : Command()
}
