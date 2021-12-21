package com.homework.coursework.customsources

import com.homework.coursework.presentation.adapter.data.UserItem
import com.homework.coursework.presentation.adapter.data.chat.ChatItem
import com.homework.coursework.presentation.adapter.data.chat.MessageItem
import com.homework.coursework.presentation.ui.chat.UpdateRecycleList
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.reset


class UpdateRecycleTests {

    val updateRecycleList: UpdateRecycleList = Mockito.spy(UpdateRecycleList::class.java)
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

    @Before
    fun setUp() {
        reset(updateRecycleList)
        testScheduler = TestScheduler()
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
    }

    @Test
    fun sameElementsMergeTest() {
        val message = messageTemplate
        val newList = listOf(message)
        val oldList = listOf(message)
        val testObserver: TestObserver<List<ChatItem>> =
            updateRecycleList(oldList = oldList, newList = newList).test()
        testObserver.apply {
            awaitTerminalEvent()
            assertNoErrors()
            assertValue { it == listOf(message) }
        }
    }

    @Test
    fun differentListsWithSameElement() {
        val message1 = messageTemplate
        val message2 = messageTemplate.copy(messageId = 1)
        val newList = listOf(message2, message1)
        val oldList = listOf(message1)
        val testObserver: TestObserver<List<ChatItem>> =
            updateRecycleList(oldList = oldList, newList = newList).test()
        testObserver.apply {
            awaitTerminalEvent()
            assertNoErrors()
            assertValue { it == listOf(message2, message1) }
        }
    }

    @Test
    fun differentLists() {
        val message1 = messageTemplate
        val message2 = messageTemplate.copy(messageId = 1)
        val newList = listOf(message2)
        val oldList = listOf(message1)
        val testObserver: TestObserver<List<ChatItem>> =
            updateRecycleList(oldList = oldList, newList = newList).test()
        testObserver.apply {
            awaitTerminalEvent()
            assertValue { it == listOf(message2, message1) }
        }
    }

    @Test
    fun updateMessage() {
        val oldList = listOf(
            messageTemplate.copy(messageContent = "netest"),
            messageTemplate.copy(messageId = 1),
            messageTemplate.copy(messageId = 2),
        )
        val newList = listOf(
            messageTemplate.copy(messageContent = "test")
        )
        val testObserver: TestObserver<List<ChatItem>> =
            updateRecycleList(oldList = oldList, newList = newList).test()
        testObserver.apply {
            awaitTerminalEvent()
            assertValue { (it[0] as MessageItem).messageContent == "test" }
        }
    }

    @Test
    fun mergeListWithSameParts() {
        val newList = listOf(
            messageTemplate.copy(messageId = 2),
            messageTemplate.copy(messageId = 1),
            messageTemplate.copy(messageContent = "netest"),
        )
        val oldList = listOf(
            messageTemplate.copy(messageContent = "test")
        )
        val testObserver: TestObserver<List<ChatItem>> =
            updateRecycleList(oldList = oldList, newList = newList).test()
        testObserver.apply {
            awaitTerminalEvent()
            assertValue {
                it == newList
            }
        }
    }

    @Test
    fun emptyOldList() {
        val newList = listOf(
            messageTemplate.copy(messageId = 2),
            messageTemplate.copy(messageId = 1),
            messageTemplate.copy(messageContent = "netest"),
        )
        val oldList = listOf<ChatItem>()
        val testObserver: TestObserver<List<ChatItem>> =
            updateRecycleList(oldList = oldList, newList = newList).test()
        testObserver.apply {
            awaitTerminalEvent()
            assertValue { it == newList }
        }
    }

    @Test
    fun emptyNewList() {
        val oldList = listOf(
            messageTemplate.copy(messageId = 2),
            messageTemplate.copy(messageId = 1),
            messageTemplate.copy(messageContent = "netest"),
        )
        val newList = listOf<ChatItem>()
        val testObserver: TestObserver<List<ChatItem>> =
            updateRecycleList(oldList = oldList, newList = newList).test()
        testObserver.apply {
            awaitTerminalEvent()
            assertValue { it == oldList }
        }
    }

    @Test
    fun mergeTwoEmptyList() {
        val testObserver: TestObserver<List<ChatItem>> =
            updateRecycleList(oldList = emptyList(), newList = emptyList()).test()
        testObserver.apply {
            awaitTerminalEvent()
            assertValue { it == emptyList<ChatItem>() }
        }
    }
}
