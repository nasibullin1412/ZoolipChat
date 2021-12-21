package com.homework.coursework.presentation.ui.chat

import com.homework.coursework.presentation.adapter.data.chat.*
import dagger.Reusable
import io.reactivex.Observable
import javax.inject.Inject

@Reusable
class DeleteRecycleListMessage @Inject constructor() :
        (List<ChatItem>, Int) -> (Observable<List<ChatItem>>) {
    override fun invoke(oldList: List<ChatItem>, deleteId: Int): Observable<List<ChatItem>> {
        return Observable.fromCallable {
            if (oldList.isEmpty()) {
                return@fromCallable oldList
            }
            val item = oldList.firstWithMessageItem { it == deleteId }
            val deleteTopicIdx = checkToDeleteTopicName(oldList, item)
            ArrayList(oldList).run {
                if (deleteTopicIdx != -1) removeAt(deleteTopicIdx)
                remove(item)
                if (lastOrNull() is DateItem) removeLast()
                forEachIndexed { idx, item -> item.id = idx }
                toList()
            }
        }
    }

    private fun checkToDeleteTopicName(oldList: List<ChatItem>, item: MessageItem): Int {
        return oldList.run {
            val positionItem = indexOf(item)
            if (positionItem == 0) {
                return NOT_FOUND_IDX
            }
            if (positionItem == lastIndex) {
                if (get(positionItem - 1) is TopicNameItem) {
                    return positionItem - 1
                }
                return NOT_FOUND_IDX
            }
            if (get(positionItem - 1) is TopicNameItem && get(positionItem + 1) is TopicNameItem) {
                return positionItem - 1
            }
            NOT_FOUND_IDX
        }
    }

    companion object {
        const val NOT_FOUND_IDX = -1
    }
}
