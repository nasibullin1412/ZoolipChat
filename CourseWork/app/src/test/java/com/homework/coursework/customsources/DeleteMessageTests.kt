package com.homework.coursework.customsources

import com.homework.coursework.presentation.adapter.data.UserItem
import com.homework.coursework.presentation.adapter.data.chat.ChatItem
import com.homework.coursework.presentation.adapter.data.chat.DateItem
import com.homework.coursework.presentation.adapter.data.chat.MessageItem
import com.homework.coursework.presentation.adapter.data.chat.TopicNameItem
import com.homework.coursework.presentation.ui.chat.DeleteRecycleListMessage
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class DeleteMessageTests {
    private val deleteRecycleListMessage: DeleteRecycleListMessage =
        Mockito.spy(DeleteRecycleListMessage::class.java)

    private lateinit var testScheduler: TestScheduler
    private val messageTemplate = MessageItem(
        messageId = 0,
        userItem = UserItem(
            0,
            0,
            "",
            "",
            "",
            5,
            3,
            false
        ),
        messageContent = "",
        emojis = ArrayList(),
        date = 3,
        isCurrentUserMessage = false,
        topicName = "f"
    )

    private val streamChatOldListTemplate = arrayListOf(
        TopicNameItem(topicName = "1"),
        messageTemplate,
        messageTemplate.copy(messageId = 1),
        TopicNameItem(topicName = "2"),
        messageTemplate.copy(messageId = 2),
        DateItem("14 декабря"),
        TopicNameItem(topicName = "3"),
        messageTemplate.copy(messageId = 3),
    )

    @Before
    fun setUp() {
        Mockito.reset(deleteRecycleListMessage)
        testScheduler = TestScheduler()
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
    }

    @Test
    fun deleteMessageFromEnd() {
        val message = messageTemplate
        val oldList = listOf(message, message.copy(messageId = 1))
        val testObserver: TestObserver<List<ChatItem>> =
            deleteRecycleListMessage(oldList = oldList, deleteId = 1).test()
        testObserver.apply {
            awaitTerminalEvent()
            assertNoErrors()
            assertValue { it == listOf(message) }
        }
    }

    @Test
    fun deleteMessageFromBegin() {
        val message = messageTemplate
        val oldList = listOf(message, message.copy(messageId = 1))
        val testObserver: TestObserver<List<ChatItem>> =
            deleteRecycleListMessage(oldList = oldList, deleteId = 0).test()
        testObserver.apply {
            awaitTerminalEvent()
            assertNoErrors()
            assertValue { it == listOf(message.copy(messageId = 1)) }
        }
    }

    @Test
    fun deleteLastMessage() {
        val message = messageTemplate
        val oldList = listOf(message)
        val testObserver: TestObserver<List<ChatItem>> =
            deleteRecycleListMessage(oldList = oldList, deleteId = 0).test()
        testObserver.apply {
            awaitTerminalEvent()
            assertNoErrors()
            assertValue { it == emptyList<ChatItem>() }
        }
    }

    @Test
    fun deleteMessageInStreamChat() {
        val oldList = streamChatOldListTemplate
        val expectedResult = streamChatOldListTemplate.apply {
            removeAt(2)
        }
        val testObserver: TestObserver<List<ChatItem>> =
            deleteRecycleListMessage(oldList = oldList, deleteId = 1).test()
        testObserver.apply {
            awaitTerminalEvent()
            assertNoErrors()
            assertValue {
                it == expectedResult
            }
        }
    }

    @Test
    fun deleteBeforeTopicNameButNotLast() {
        val oldList = streamChatOldListTemplate
        val expectedResult = streamChatOldListTemplate.apply {
            removeAt(1)
        }
        val testObserver: TestObserver<List<ChatItem>> =
            deleteRecycleListMessage(oldList = oldList, deleteId = 0).test()
        testObserver.apply {
            awaitTerminalEvent()
            assertNoErrors()
            assertValue {
                it == expectedResult
            }
        }
    }

    @Test
    fun deleteLastTopicMessage() {
        val oldList = streamChatOldListTemplate
        val deleteList = streamChatOldListTemplate.subList(5, 8)
        val expectedResult = streamChatOldListTemplate.apply {
            removeAll(deleteList)
        }
        val testObserver: TestObserver<List<ChatItem>> =
            deleteRecycleListMessage(oldList = oldList, deleteId = 3).test()
        testObserver.apply {
            awaitTerminalEvent()
            assertNoErrors()
            assertValue {
                it == expectedResult
            }
        }
    }
}
