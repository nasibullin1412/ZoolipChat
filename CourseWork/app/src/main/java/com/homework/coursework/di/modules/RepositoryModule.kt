package com.homework.coursework.di.modules

import com.homework.coursework.data.MessageRepositoryImpl
import com.homework.coursework.data.ReactionRepositoryImpl
import com.homework.coursework.data.StreamRepositoryImpl
import com.homework.coursework.data.UserRepositoryImpl
import com.homework.coursework.domain.repository.MessageRepository
import com.homework.coursework.domain.repository.ReactionRepository
import com.homework.coursework.domain.repository.StreamRepository
import com.homework.coursework.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
interface RepositoryModule {
    @Singleton
    @Binds
    fun bindMessageRepository(messageRepository: MessageRepositoryImpl): MessageRepository

    @Singleton
    @Binds
    fun bindReactionRepository(reactionRepository: ReactionRepositoryImpl): ReactionRepository

    @Singleton
    @Binds
    fun bindStreamRepository(streamRepository: StreamRepositoryImpl): StreamRepository

    @Singleton
    @Binds
    fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository
}
