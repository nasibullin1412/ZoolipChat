package com.homework.coursework.presentation.ui.chat.elm

import android.os.Parcelable
import com.homework.coursework.presentation.adapter.data.EmojiItem
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.adapter.data.chat.ChatItem
import com.homework.coursework.presentation.adapter.data.chat.MessageItem
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.io.InputStream

@Parcelize
data class State(
    val itemList: @RawValue List<ChatItem> = emptyList(),
    val error: Throwable? = null,
    val messageId: Int = 0,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isUpdate: Boolean = false,
    val isFirstLoad: Boolean = false
) : Parcelable

sealed class Event {

    sealed class Ui : Event() {
        class LoadFirstPage(
            val streamItem: StreamItem,
            val topicItem: TopicItem,
            val currId: Int
        ) : Ui()

        class LoadNextPage(
            val streamItem: StreamItem,
            val topicItem: TopicItem,
            val currId: Int
        ) : Ui()

        class UpdateMessage(
            val streamItem: StreamItem,
            val topicItem: TopicItem,
            val currId: Int
        ) : Ui()

        class SendMessage(
            val streamItem: StreamItem,
            val topicItem: TopicItem,
            val content: String
        ) : Ui()

        class DeleteMessageFromRecycle(
            val oldList: List<ChatItem>,
            val messageId: Int
        ) : Ui()

        class AddReaction(val messageItem: MessageItem, val emojiItem: EmojiItem) : Ui()

        class DeleteMessage(val messageItem: MessageItem) : Ui()

        class DeleteReaction(val messageItem: MessageItem, val emojiItem: EmojiItem) : Ui()

        object GetCurrentId : Ui()

        class AddFile(
            val streamItem: StreamItem,
            val topicItem: TopicItem,
            val input: InputStream,
            val fileName: String
        ) : Ui()

        class MergeOldList(
            val oldList: List<ChatItem>,
            val newList: List<ChatItem>
        ) : Ui()

        class SaveMessage(
            val streamItem: StreamItem,
            val topicItem: TopicItem,
            val messages: List<ChatItem>,
            val currId: Int
        ) : Ui()
    }

    sealed class Internal : Event() {

        class InitPageLoaded(val itemList: List<ChatItem>) : Internal()

        class PageLoaded(val itemList: List<ChatItem>) : Internal()

        class MessageAdded(val id: Int) : Internal()

        object MessageDeleted : Internal()

        class UpdateRecycle(val itemList: List<ChatItem>) : Internal()

        object ReactionChanged : Internal()

        object MessagesSaved : Internal()

        class ErrorCommands(val error: Throwable) : Internal()

        class ErrorLoading(val error: Throwable) : Internal()

        class SuccessGetId(val currId: Int) : Internal()
    }
}

sealed class Effect {
    class NextPageLoadError(val error: Throwable) : Effect()

    class PageLoaded(val itemList: List<ChatItem>) : Effect()

    class MessageAdded(val id: Int) : Effect()

    object DeleteMessage : Effect()

    object ReactionChanged : Effect()

    object MessagesSaved : Effect()

    class ErrorCommands(val error: Throwable) : Effect()

    class SuccessGetId(val currId: Int) : Effect()
}

sealed class Command {

    class LoadFirstPage(
        val streamItem: StreamItem,
        val topicItem: TopicItem,
        val currId: Int
    ) : Command()

    class LoadMessages(
        val streamItem: StreamItem,
        val topicItem: TopicItem,
        val currId: Int
    ) : Command()

    class UpdateMessage(
        val streamItem: StreamItem,
        val topicItem: TopicItem,
        val currId: Int
    ) : Command()

    class SendMessage(
        val streamItem: StreamItem,
        val topicItem: TopicItem,
        val content: String
    ) : Command()

    class AddReaction(val messageItem: MessageItem, val emojiItem: EmojiItem) : Command()

    class DeleteReaction(val messageItem: MessageItem, val emojiItem: EmojiItem) : Command()

    class MergeWithNewMessageList(
        val oldList: List<ChatItem>,
        val newList: List<ChatItem>
    ) : Command()

    class SaveMessage(
        val streamItem: StreamItem,
        val topicItem: TopicItem,
        val messages: List<ChatItem>,
        val currId: Int
    ) : Command()

    class DeleteMessage(val messageItem: MessageItem) : Command()

    class DeleteMessageFromRecycle(val oldList: List<ChatItem>, val deleteId: Int) : Command()

    object GetCurrentId : Command()

    class AddFile(
        val streamItem: StreamItem,
        val topicItem: TopicItem,
        val input: InputStream,
        val fileName: String
    ) : Command()
}
