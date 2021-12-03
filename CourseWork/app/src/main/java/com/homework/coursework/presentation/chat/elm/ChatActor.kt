package com.homework.coursework.presentation.chat.elm

import com.homework.coursework.domain.usecase.*
import com.homework.coursework.presentation.adapter.data.DiscussItem
import com.homework.coursework.presentation.adapter.mapper.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import vivid.money.elmslie.core.ActorCompat

class ChatActor(
    private val getTopicMessages: GetTopicMessagesUseCase,
    private val addReactionToMessage: AddReactionToMessageUseCase,
    private val deleteReactionToMessage: DeleteReactionToMessageUseCase,
    private val addMessages: AddMessageUseCase,
    private val initMessages: InitMessageUseCase,
    private val saveMessage: SaveMessageUseCase,
    private val getCurrentId: GetCurrentUserIdUseCase,
    private val streamDataMapper: StreamDataMapper,
    private val topicDataMapper: TopicDataMapper,
    private val discussToItemMapper: DiscussItemMapper,
    private val messageDataMapper: MessageDataMapper,
    private val messageListDataMapper: MessageListDataMapper,
    private val emojiDataMapper: EmojiDataMapper
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
            ).map { discussToItemMapper(messageDataList = it, currId = command.currId) }
                .mapEvents(
                    { list -> Event.Internal.InitPageLoaded(list) },
                    { error -> Event.Internal.ErrorLoading(error) }
                )
        }

        is Command.LoadNextPage -> {
            getTopicMessages(
                streamData = streamDataMapper(command.streamItem),
                topicData = topicDataMapper(command.topicItem),
                currId = command.currId
            ).map { discussToItemMapper(messageDataList = it, currId = command.currId) }
                .mapEvents(
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
            doCreateNewRecycleList(oldList = command.oldList, newList = command.newList)
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
    }

    private fun doCreateNewRecycleList(
        oldList: List<DiscussItem>,
        newList: List<DiscussItem>
    ): Observable<List<DiscussItem>> {
        return Observable.fromCallable {
            getNewRecycleList(oldList = oldList, newList = newList)
        }.subscribeOn(Schedulers.computation())
            .map { discussList ->
                discussList.forEachIndexed { i, element -> element.id = i }
                discussList
            }
    }

    private fun getNewRecycleList(
        oldList: List<DiscussItem>,
        newList: List<DiscussItem>
    ): List<DiscussItem> {
        val lastElemOfNew = newList.last()
        val idx =
            oldList.indexOfFirst { it.messageItem?.messageId == lastElemOfNew.messageItem?.messageId }
        if (idx == -1) {
            return newList + oldList
        }
        val resultList: MutableList<DiscussItem> = oldList.toMutableList()
        val (untilIdx, remain) = getUntilIdx(
            idx = idx,
            newSize = newList.size
        )
        for (i in idx downTo untilIdx) {
            val newIdx = newList.lastIndex - idx + i
            resultList[i] = newList[newIdx]
        }
        for (i in remain - 1 downTo 0) {
            resultList.add(0, newList[i])
        }
        return resultList
    }

    private fun getUntilIdx(idx: Int, newSize: Int): Pair<Int, Int> {
        val remainElemNumber = idx - newSize + 1
        return if (remainElemNumber >= 0) {
            Pair(remainElemNumber, 0)
        } else {
            Pair(0, -remainElemNumber)
        }
    }
}
