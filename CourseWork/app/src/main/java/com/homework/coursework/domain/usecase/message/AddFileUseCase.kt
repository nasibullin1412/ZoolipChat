package com.homework.coursework.domain.usecase.message

import com.homework.coursework.domain.entity.StreamData
import com.homework.coursework.domain.entity.TopicData
import com.homework.coursework.domain.repository.MessageRepository
import io.reactivex.Observable
import java.io.InputStream
import javax.inject.Inject

interface AddFileUseCase : (StreamData, TopicData, InputStream, String) -> (Observable<Int>) {
    override fun invoke(
        stream: StreamData,
        topic: TopicData,
        input: InputStream,
        fileName: String
    ): Observable<Int>
}

class AddFileUseCaseImpl @Inject constructor(
    private val messageRepository: MessageRepository
) : AddFileUseCase {
    override fun invoke(
        stream: StreamData,
        topic: TopicData,
        input: InputStream,
        fileName: String
    ): Observable<Int> {
        return messageRepository.addFile(
            streamData = stream,
            topicData = topic,
            inputStream = input,
            file = fileName
        )
    }
}
