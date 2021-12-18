package com.homework.coursework.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.homework.coursework.databinding.PeopleItemBinding
import com.homework.coursework.presentation.adapter.data.UserItem
import com.homework.coursework.presentation.callbacks.PeopleCallback
import com.homework.coursework.presentation.interfaces.UserItemCallback
import com.homework.coursework.presentation.viewholder.PeopleViewHolder
import dagger.Reusable
import javax.inject.Inject

@Reusable
class PeopleAdapter @Inject constructor() :
    ListAdapter<UserItem, PeopleViewHolder>(PeopleCallback()) {

    private var userItemCallback: UserItemCallback? = null

    fun setUserListener(userItemCallback: UserItemCallback?) {
        this.userItemCallback = userItemCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return PeopleViewHolder(
            PeopleItemBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            userItemCallback?.onUserItemClick(getItem(position).userId)
        }
        holder.bind(getItem(position))
    }
}
