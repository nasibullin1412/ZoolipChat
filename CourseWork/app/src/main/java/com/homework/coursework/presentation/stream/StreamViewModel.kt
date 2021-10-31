package com.homework.coursework.presentation.stream

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.coursework.domain.usecase.*
import com.homework.coursework.presentation.adapter.mapper.StreamItemMapper
import com.homework.coursework.presentation.adapter.mapper.TopicItemMapper

class StreamViewModel : ViewModel() {
    private var _streamScreenState: MutableLiveData<StreamScreenState> = MutableLiveData()
    val streamScreenState: LiveData<StreamScreenState>
        get() = _streamScreenState

    private var _topicScreenState: MutableLiveData<TopicScreenState> = MutableLiveData()
    val topicScreenState: LiveData<TopicScreenState>
        get() = _topicScreenState

    private val getAllStreamsUseCase: GetAllStreamsUseCase = GetAllStreamsUseCaseImpl()
    private val getSubscribedStreamsUseCase: GetSubscribedStreamsUseCase =
        GetSubscribedStreamsUseCaseImpl()
    private val streamToItemMapper: StreamItemMapper = StreamItemMapper()

    private val getStreamTopicsUseCase: GetStreamTopicsUseCase = GetStreamTopicsUseCaseImpl()
    private val topicToItemMapper: TopicItemMapper = TopicItemMapper()

    fun getTopics(idStream: Int) {
        _topicScreenState.value =
            TopicScreenState.Result(topicToItemMapper(getStreamTopicsUseCase(idStream)), idStream)
    }

    fun getAllStreams() {
        _streamScreenState.value =
            StreamScreenState.Result(streamToItemMapper(getAllStreamsUseCase()))
    }

    fun getSubscribedStream() {
        _streamScreenState.value =
            StreamScreenState.Result(streamToItemMapper(getSubscribedStreamsUseCase()))
    }
}
