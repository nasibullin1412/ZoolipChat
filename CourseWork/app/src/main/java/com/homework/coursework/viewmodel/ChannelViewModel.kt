package com.homework.coursework.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChannelViewModel  : ViewModel() {

    val query: LiveData<String> get() = _query
    private val _query = MutableLiveData<String>()

    fun doQueryTransfer(queryText: String?) {
        _query.value = queryText ?: ""
    }
}
