package com.homework.coursework.presentation.ui.editmessage.elm

import com.homework.coursework.domain.usecase.message.EditMessageUseCase
import com.homework.coursework.presentation.adapter.mapper.MessageDataMapper
import io.reactivex.Observable
import vivid.money.elmslie.core.ActorCompat
import javax.inject.Inject

class EditMessageActor(
    private val editMessage: EditMessageUseCase,
    private val messageDataMapper: MessageDataMapper
) : ActorCompat<Command, Event> {

    override fun execute(command: Command): Observable<Event> = when (command) {
        is Command.EditMessage -> {
            editMessage(
                messageData = messageDataMapper(command.messageData)
            ).mapEvents(Event.Internal.SuccessEditMessage) { error ->
                Event.Internal.ErrorEdit(error)
            }
        }
    }
}
