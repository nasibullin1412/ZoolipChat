package com.homework.coursework.di.modules

import com.homework.coursework.data.*
import com.homework.coursework.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface RepositoryModule {
    @Reusable
    @Binds
    fun bindMessageRepository(messageRepository: MessageRepositoryImpl): MessageRepository

    @Reusable
    @Binds
    fun bindReactionRepository(reactionRepository: ReactionRepositoryImpl): ReactionRepository

    @Reusable
    @Binds
    fun bindStreamRepository(streamRepository: StreamRepositoryImpl): StreamRepository

    @Reusable
    @Binds
    fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository

    @Reusable
    @Binds
    fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Reusable
    @Binds
    fun bindSubscribeStreamRepository(subscribeStreamRepository: SubscribeStreamRepositoryImpl):
            SubscribeStreamRepository
}
