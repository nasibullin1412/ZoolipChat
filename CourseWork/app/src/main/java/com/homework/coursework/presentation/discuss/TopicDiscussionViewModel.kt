package com.homework.coursework.presentation.discuss

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.coursework.domain.usecase.*
import com.homework.coursework.presentation.adapter.data.*
import com.homework.coursework.presentation.adapter.mapper.*
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

    /**
     * it is emulate sent messages
     */
    private val sentDiscusses: MutableList<DiscussItem> = mutableListOf()

    fun getMessages(stream: StreamItem, topic: TopicItem) {
        getTopicMessagesUseCase(streamDataMapper(stream), topicDataMapper(topic))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map(discussToItemMapper)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    _topicDiscScreenState.value =
                        TopicDiscussionState.ResultMessages(it + sentDiscusses)
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
                onComplete = {
                    _topicDiscScreenState.value = TopicDiscussionState.ResultUserChanges
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
