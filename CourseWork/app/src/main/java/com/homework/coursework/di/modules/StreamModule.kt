package com.homework.coursework.di.modules

import com.homework.coursework.di.AllStreams
import com.homework.coursework.di.StreamFragmentScope
import com.homework.coursework.di.SubscribedStreams
import com.homework.coursework.domain.usecase.GetAllStreamsUseCase
import com.homework.coursework.domain.usecase.GetSubscribedStreamsUseCase
import com.homework.coursework.domain.usecase.SearchAllStreamsUseCase
import com.homework.coursework.domain.usecase.SearchSubscribeStreamsUseCase
import com.homework.coursework.presentation.adapter.mapper.StreamItemMapper
import com.homework.coursework.presentation.stream.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.core.store.Store

@Module
class StreamModule {

    @StreamFragmentScope
    @Provides
    fun provideStreamActor(
        streamItemMapper: StreamItemMapper,
        getAllStreams: GetAllStreamsUseCase,
        getSubscribedStreams: GetSubscribedStreamsUseCase,
        searchAllStreams: SearchAllStreamsUseCase,
        searchSubscribedStreams: SearchSubscribeStreamsUseCase
    ): StreamActor {
        return StreamActor(
            streamToItemMapper = streamItemMapper,
            getAllStreams = getAllStreams,
            getSubscribedStreams = getSubscribedStreams,
            searchAllStream = searchAllStreams,
            searchSubscribedStreams = searchSubscribedStreams
        )
    }

    @StreamFragmentScope
    @Provides
    @SubscribedStreams
    fun provideSubscribedStreamStore(
        streamActor: StreamActor,
        streamReducer: StreamReducer
    ): Store<Event, Effect, State> {
        return StreamStoreFactory(streamActor, streamReducer).provide()
    }

    @StreamFragmentScope
    @Provides
    @AllStreams
    fun provideAllStreamStore(
        streamActor: StreamActor,
        streamReducer: StreamReducer
    ): Store<Event, Effect, State> {
        return StreamStoreFactory(streamActor, streamReducer).provide()
    }
}
