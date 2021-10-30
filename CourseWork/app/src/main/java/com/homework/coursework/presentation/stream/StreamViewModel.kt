package com.homework.coursework.presentation.stream

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.coursework.domain.usecase.GetAllStreamsUseCase
import com.homework.coursework.domain.usecase.GetAllStreamsUseCaseImpl
import com.homework.coursework.domain.usecase.GetSubscribedStreamsUseCase
import com.homework.coursework.domain.usecase.GetSubscribedStreamsUseCaseImpl
import com.homework.coursework.presentation.adapter.mapper.StreamItemMapper

class StreamViewModel : ViewModel() {
    private var _streamScreenState: MutableLiveData<StreamScreenState> = MutableLiveData()
    val streamScreenState: LiveData<StreamScreenState>
        get() = _streamScreenState

    private val getAllStreamsUseCase: GetAllStreamsUseCase = GetAllStreamsUseCaseImpl()
    private val getSubscribedStreamsUseCase: GetSubscribedStreamsUseCase =
        GetSubscribedStreamsUseCaseImpl()
    private val streamToItemMapper: StreamItemMapper = StreamItemMapper()

    fun getAllStreams() {
        _streamScreenState.value =
            StreamScreenState.Result(streamToItemMapper(getAllStreamsUseCase()))
    }

    fun getSubscribedStream() {
        _streamScreenState.value =
            StreamScreenState.Result(streamToItemMapper(getSubscribedStreamsUseCase()))
    }
}
