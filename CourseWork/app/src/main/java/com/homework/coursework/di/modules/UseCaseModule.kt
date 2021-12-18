package com.homework.coursework.di.modules

import com.homework.coursework.domain.usecase.auth.AuthUserUseCase
import com.homework.coursework.domain.usecase.auth.AuthUserUseCaseImpl
import com.homework.coursework.domain.usecase.auth.CheckAuthUseCase
import com.homework.coursework.domain.usecase.auth.CheckAuthUseCaseImpl
import com.homework.coursework.domain.usecase.message.*
import com.homework.coursework.domain.usecase.streams.*
import com.homework.coursework.domain.usecase.users.*
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface UseCaseModule {

    @Reusable
    @Binds
    fun bindAddMessage(addMessage: AddMessageUseCaseImpl): AddMessageUseCase

    @Reusable
    @Binds
    fun bindAddReaction(addReaction: AddReactionToMessageUseCaseImpl): AddReactionToMessageUseCase

    @Reusable
    @Binds
    fun bindDeleteReactionToMessage(deleteReactionToMessage: DeleteReactionToMessageUseCaseImpl):
            DeleteReactionToMessageUseCase

    @Reusable
    @Binds
    fun bindGetAllStreams(addMessage: GetAllStreamsUseCaseImpl): GetAllStreamsUseCase

    @Reusable
    @Binds
    fun bindGetMe(addMessage: GetMeUseCaseImpl): GetMeUseCase

    @Reusable
    @Binds
    fun bindGetSubscribedStreams(getSubscribedStreams: GetSubscribedStreamsUseCaseImpl):
            GetSubscribedStreamsUseCase

    @Reusable
    @Binds
    fun bindGetTopicMessages(getMessages: GetMessagesUseCaseImpl):
            GetMessagesUseCase

    @Reusable
    @Binds
    fun bindInitTopicMessages(getTopicMessages: InitMessageUseCaseImpl): InitMessageUseCase

    @Reusable
    @Binds
    fun bindSearchAllStreams(searchAllStreams: SearchAllStreamsUseCaseImpl): SearchAllStreamsUseCase

    @Reusable
    @Binds
    fun bindSearchSubscribedStreams(searchAllStreams: SearchSubscribeStreamsUseCaseImpl):
            SearchSubscribeStreamsUseCase

    @Reusable
    @Binds
    fun bindGetUserProfile(getUserProfile: GetUserProfileUseCaseImpl): GetUserProfileUseCase

    @Reusable
    @Binds
    fun bindSaveMessageUseCase(saveMessageUseCase: SaveMessageUseCaseImpl): SaveMessageUseCase

    @Reusable
    @Binds
    fun bindAuthUserUseCase(authUserUseCase: AuthUserUseCaseImpl): AuthUserUseCase

    @Reusable
    @Binds
    fun bindCheckAuthUseCase(checkAuthUseCase: CheckAuthUseCaseImpl): CheckAuthUseCase

    @Reusable
    @Binds
    fun bindGetCurrentUserId(currentUserIdUseCase: GetCurrentUserIdUseCaseImpl): GetCurrentUserIdUseCase

    @Reusable
    @Binds
    fun bindLogoutUser(logoutUserUseCase: LogoutUserUseCaseImpl): LogoutUserUseCase

    @Reusable
    @Binds
    fun bindGetAllUsers(getAllUsersUseCase: GetAllUsersUseCaseImpl): GetAllUsersUseCase

    @Reusable
    @Binds
    fun bindSearchUsers(searchUsersUseCase: SearchUsersUseCaseImpl): SearchUsersUseCase

    @Reusable
    @Binds
    fun bindSubscribeToStream(subscribeToStreamUseCase: SubscribeToStreamUseCaseImpl):
            SubscribeToStreamUseCase

    @Reusable
    @Binds
    fun bindUpdateMessage(updateMessageUseCase: UpdateMessageUseCaseImpl): UpdateMessageUseCase

    @Reusable
    @Binds
    fun bindDeleteMessage(deleteMessageUseCase: DeleteMessageUseCaseImpl): DeleteMessageUseCase

    @Reusable
    @Binds
    fun bindEditMessageUseCase(editMessageUseCase: EditMessageUseCaseImpl): EditMessageUseCase
}
