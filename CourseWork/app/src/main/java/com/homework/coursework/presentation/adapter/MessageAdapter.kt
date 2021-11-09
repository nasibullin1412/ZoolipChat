package com.homework.coursework.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homework.coursework.R
import com.homework.coursework.presentation.adapter.data.DiscussItem
import com.homework.coursework.presentation.callbacks.MessageCallback
import com.homework.coursework.presentation.interfaces.MessageItemCallback
import com.homework.coursework.presentation.viewholder.DateViewHolder
import com.homework.coursework.presentation.viewholder.MessageFromViewHolder
import com.homework.coursework.presentation.viewholder.MessageToViewHolder

class MessageAdapter : ListAdapter<DiscussItem, RecyclerView.ViewHolder>(MessageCallback()) {

    private lateinit var listener: MessageItemCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            MESSAGE_TO -> MessageToViewHolder(
                listener,
                inflater.inflate(R.layout.message_to_item, parent, false)
            )
            MESSAGE_FROM -> MessageFromViewHolder(
                listener,
                inflater.inflate(R.layout.message_from_item, parent, false)
            )
            else -> DateViewHolder(
                inflater.inflate(R.layout.date_item, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MessageToViewHolder -> holder.bind(
                getItem(position).messageItem
                    ?: throw IllegalArgumentException("messageData required"),
                position
            )
            is MessageFromViewHolder -> holder.bind(
                getItem(position).messageItem
                    ?: throw IllegalArgumentException("messageData required"),
                position
            )
            is DateViewHolder -> {
                holder.bind(
                    getItem(position).date
                        ?: throw IllegalArgumentException("date required")
                )
                return
            }
        }
        holder.itemView.setOnLongClickListener {
            listener.getBottomSheet(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position).messageItem == null) {
            return DATE
        }
        return if (getItem(position).messageItem?.isCurrentUserMessage == true) {
            MESSAGE_TO
        } else {
            MESSAGE_FROM
        }
    }

    fun initListener(listener: MessageItemCallback) {
        this.listener = listener
    }

    companion object {
        private const val MESSAGE_TO = 0
        private const val MESSAGE_FROM = 1
        private const val DATE = 2
    }
}
