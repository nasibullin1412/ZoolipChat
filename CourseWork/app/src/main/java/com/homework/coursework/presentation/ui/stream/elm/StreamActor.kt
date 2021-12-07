package com.homework.coursework.presentation.ui.stream.elm

import com.homework.coursework.domain.usecase.GetAllStreamsUseCase
import com.homework.coursework.domain.usecase.GetSubscribedStreamsUseCase
import com.homework.coursework.domain.usecase.SearchAllStreamsUseCase
import com.homework.coursework.domain.usecase.SearchSubscribeStreamsUseCase
import com.homework.coursework.presentation.adapter.mapper.StreamItemMapper
import io.reactivex.Observable
import vivid.money.elmslie.core.ActorCompat

class StreamActor(
    private val streamToItemMapper: StreamItemMapper,
    private val getAllStreams: GetAllStreamsUseCase,
    private val getSubscribedStreams: GetSubscribedStreamsUseCase,
    private val searchAllStream: SearchAllStreamsUseCase,
    private val searchSubscribedStreams: SearchSubscribeStreamsUseCase,
) : ActorCompat<Command, Event> {
    override fun execute(command: Command): Observable<Event> {
        val useCase = when (command) {
            Command.LoadAllStreams -> getAllStreams()
            Command.LoadSubscribedStreams -> getSubscribedStreams()
            is Command.SearchStreams -> searchAllStream(command.query)
            is Command.SearchSubscribedStreams -> searchSubscribedStreams(command.query)
        }
        return useCase
            .map(streamToItemMapper)
            .mapEvents(
                { list -> Event.Internal.StreamLoaded(list) },
                { error -> Event.Internal.ErrorLoading(error = error) }
            )
    }
}
