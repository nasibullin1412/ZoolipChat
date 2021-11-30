package com.homework.coursework.di.modules

import com.homework.coursework.data.*
import com.homework.coursework.domain.repository.*
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

    @Singleton
    @Binds
    fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository
}
