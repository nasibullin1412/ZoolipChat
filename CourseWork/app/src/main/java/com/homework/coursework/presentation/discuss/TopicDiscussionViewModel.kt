package com.homework.coursework.presentation.discuss

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.coursework.domain.usecase.*
import com.homework.coursework.presentation.adapter.data.*
import com.homework.coursework.presentation.adapter.mapper.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class TopicDiscussionViewModel : ViewModel() {
    private var _topicDiscScreenState: MutableLiveData<TopicDiscussionState> = MutableLiveData()
    val topicDiscScreenState: LiveData<TopicDiscussionState>
        get() = _topicDiscScreenState

    private val getTopicMessagesUseCase: GetTopicMessagesUseCase = GetTopicMessagesUseCaseImpl()
    private val addReactionToMessageUse: AddReactionToMessageUseCase =
        AddReactionToMessageUseCaseImpl()
    private val deleteReactionToMessageUseCase: DeleteReactionToMessageUseCaseImpl =
        DeleteReactionToMessageUseCaseImpl()
    private val addMessagesUseCase: AddMessageUseCase = AddMessageUseCaseImpl()

    private val discussToItemMapper: DiscussItemMapper = DiscussItemMapper()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val streamDataMapper: StreamDataMapper = StreamDataMapper()
    private val topicDataMapper: TopicDataMapper = TopicDataMapper()
    private val messageDataMapper: MessageDataMapper = MessageDataMapper()
    private val emojiDataMapper: EmojiDataMapper = EmojiDataMapper()


    fun getMessages(stream: StreamItem, topic: TopicItem) {
        getTopicMessagesUseCase(streamDataMapper(stream), topicDataMapper(topic))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map(discussToItemMapper)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    _topicDiscScreenState.value =
                        TopicDiscussionState.ResultMessages(it)
                },
                onError = {
                    _topicDiscScreenState.value = TopicDiscussionState.Error(it)
                }
            )
            .addTo(compositeDisposable)
    }

    fun doChanges(
        useCaseTypeReaction: UseCaseTypeReaction,
        messageItem: MessageItem,
        emojiItem: EmojiItem
    ) {
        getNeedUseCase(
            useCaseTypeReaction = useCaseTypeReaction,
            messageItem = messageItem,
            emojiItem = emojiItem
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    _topicDiscScreenState.value = TopicDiscussionState.ResultUserChanges
                },
                onError = {
                    _topicDiscScreenState.value = TopicDiscussionState.ErrorUserChanges(it)
                }
            )
            .addTo(compositeDisposable)
    }

    fun doChanges(
        useCaseTypeMessage: UseCaseTypeMessage,
        streamItem: StreamItem,
        topicItem: TopicItem,
        content: String
    ) {
        getNeedUseCase(
            useCaseTypeMessage = useCaseTypeMessage,
            streamItem = streamItem,
            topicItem = topicItem,
            content = content
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    _topicDiscScreenState.value = TopicDiscussionState.AddMessageResult(it)
                },
                onError = {
                    _topicDiscScreenState.value = TopicDiscussionState.ErrorUserChanges(it)
                }
            )
            .addTo(compositeDisposable)
    }

    private fun getNeedUseCase(
        useCaseTypeReaction: UseCaseTypeReaction,
        messageItem: MessageItem,
        emojiItem: EmojiItem
    ) = when (useCaseTypeReaction) {
        UseCaseTypeReaction.ADD_REACTION -> addReactionToMessageUse(
            messageData = messageDataMapper(messageItem),
            emojiData = emojiDataMapper(emojiItem)
        )
        UseCaseTypeReaction.DELETE_REACTION -> deleteReactionToMessageUseCase(
            messageData = messageDataMapper(messageItem),
            emojiData = emojiDataMapper(emojiItem)
        )
    }

    private fun getNeedUseCase(
        useCaseTypeMessage: UseCaseTypeMessage,
        streamItem: StreamItem,
        topicItem: TopicItem,
        content: String
    ) = when (useCaseTypeMessage) {
        UseCaseTypeMessage.ADD_MESSAGE -> addMessagesUseCase(
            streamData = streamDataMapper(streamItem),
            topicData = topicDataMapper(topicItem),
            content = content
        )
        UseCaseTypeMessage.DELETE_MESSAGE -> throw NotImplementedError()
    }

    fun doCreateNewRecycleList(oldList: List<DiscussItem>, newList: List<DiscussItem>) {
        Observable.fromCallable {
            getNewRecycleList(oldList = oldList, newList = newList)
        }
            .subscribeOn(Schedulers.computation())
            .map{discussList ->
                discussList.forEachIndexed { i, element -> element.id = i }
                discussList
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    _topicDiscScreenState.value = TopicDiscussionState.UpdateRecycleMessages(it)
                },
                onError = {
                    _topicDiscScreenState.value = TopicDiscussionState.Error(it)
                }
            )
            .addTo(compositeDisposable)
    }

    private fun getNewRecycleList(
        oldList: List<DiscussItem>,
        newList: List<DiscussItem>
    ): List<DiscussItem> {
        val lastElemOfNew = newList.last()
        val idx = oldList.indexOfFirst { it.messageItem?.messageId == lastElemOfNew.messageItem?.messageId }
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
