package com.homework.coursework.presentation.ui.chat.utils

import androidx.recyclerview.widget.LinearLayoutManager
import com.homework.coursework.presentation.adapter.ChatAdapter
import com.homework.coursework.presentation.adapter.data.chat.MessageItem
import com.homework.coursework.presentation.adapter.data.chat.firstWithInstance
import com.homework.coursework.presentation.ui.chat.ChatBaseFragment
import com.homework.coursework.presentation.ui.chat.PagingScrollListener
import com.homework.coursework.presentation.ui.chat.elm.Event

internal fun ChatBaseFragment.initRecycleViewImpl() {
    with(binding.rvMessage) {
        chatAdapter = ChatAdapter().apply { initMessageListener(this@initRecycleViewImpl) }
        adapter = chatAdapter
        val currLayoutManager = LinearLayoutManager(context).apply { stackFromEnd = true }
        layoutManager = currLayoutManager
        scrollListener = object : PagingScrollListener(currLayoutManager) {
            override fun onLoadMore(top: Boolean): Boolean {
                val numberOfMess =
                    chatAdapter.currentList.firstWithInstance<MessageItem>()?.messageId ?: 0
                internalStore.accept(
                    Event.Ui.LoadNextPage(
                        streamItem = currentStream,
                        topicItem = currentTopic.copy(numberOfMess = numberOfMess),
                        currId = currId
                    )
                )
                return true
            }
        }
        addOnScrollListener(scrollListener)
    }
}
