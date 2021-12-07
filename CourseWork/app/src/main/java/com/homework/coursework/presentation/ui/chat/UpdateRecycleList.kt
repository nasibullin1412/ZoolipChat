package com.homework.coursework.presentation.ui.chat

import com.homework.coursework.presentation.adapter.data.ChatItem
import dagger.Reusable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@Reusable
class UpdateRecycleList @Inject constructor(): (List<ChatItem>, List<ChatItem>) -> Observable<List<ChatItem>> {

    override fun invoke(
        oldList: List<ChatItem>,
        newList: List<ChatItem>
    ): Observable<List<ChatItem>> {
        return Observable.fromCallable {
            getNewRecycleList(oldList = oldList, newList = newList)
        }.subscribeOn(Schedulers.computation())
            .map { chatList ->
                chatList.forEachIndexed { i, element -> element.id = i }
                chatList
            }
    }

    private fun getNewRecycleList(
        oldList: List<ChatItem>,
        newList: List<ChatItem>
    ): List<ChatItem> {
        val lastElemOfNew = newList.last()
        val idx =
            oldList.indexOfFirst { it.messageItem?.messageId == lastElemOfNew.messageItem?.messageId }
        if (idx == -1) {
            return newList + oldList
        }
        val resultList: MutableList<ChatItem> = oldList.toMutableList()
        val (untilIdx, remain) = getUntilIdx(
            idx = idx,
            newSize = newList.size
        )
        for (i in idx downTo untilIdx) {
            val newIdx = newList.lastIndex - idx + i
            resultList[i] = newList[newIdx]
        }
        for (i in remain - 1 downTo 0) {
            resultList.add(0, newList[i])
        }
        return resultList
    }

    private fun getUntilIdx(idx: Int, newSize: Int): Pair<Int, Int> {
        val remainElemNumber = idx - newSize + 1
        return if (remainElemNumber >= 0) {
            Pair(remainElemNumber, 0)
        } else {
            Pair(0, -remainElemNumber)
        }
    }
}
