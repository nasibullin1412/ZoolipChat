package com.homework.coursework.di.modules

import com.homework.coursework.domain.usecase.*
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
    fun bindGetTopicMessages(getTopicMessages: GetTopicMessagesUseCaseImpl):
            GetTopicMessagesUseCase

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
}
