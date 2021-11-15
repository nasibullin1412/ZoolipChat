package com.homework.coursework.presentation.discuss

import android.util.Log
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PagingScrollListener(
    private val layoutManager: RecyclerView.LayoutManager
) : RecyclerView.OnScrollListener() {

    private var currentPage = START_PAGE_INDEX
    private var previousTotalItemCount = 0
    private var loading = true
    private var needsLoad = true
    private val visibleThreshold = 5

    fun blockLoading(){
        needsLoad = false
    }

    fun unBlockLoading(){
        needsLoad = true
    }


    override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (needsLoad.not()) {
            return
        }
        var firstVisibleItemPosition = 0
        val totalItemCount: Int = layoutManager.itemCount
        if (layoutManager is LinearLayoutManager) {
            firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        }

        if (totalItemCount < previousTotalItemCount) {
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && firstVisibleItemPosition < 5) {
            onLoadMore(true, totalItemCount, recyclerView)
            loading = true
        }
    }

    abstract fun onLoadMore(top: Boolean, totalItemsCount: Int, view: RecyclerView?): Boolean

    companion object {
        const val START_PAGE_INDEX = 1
    }
}