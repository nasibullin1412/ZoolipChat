package com.homework.coursework.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homework.coursework.R
import com.homework.coursework.callbacks.MessageCallback
import com.homework.coursework.data.MessageData
import com.homework.coursework.interfaces.MessageItemCallback
import com.homework.coursework.viewholders.DateViewHolder
import com.homework.coursework.viewholders.MessageFromViewHolder
import com.homework.coursework.viewholders.MessageToViewHolder

class MessageAdapter(private val curId: Int) :
    ListAdapter<MessageData, RecyclerView.ViewHolder>(MessageCallback()) {

    var dates: Map<String, Int> = HashMap()
    private lateinit var listener: MessageItemCallback
    private var currentDate = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            MESSAGE_TO -> MessageToViewHolder(
                inflater.inflate(R.layout.message_to_item, parent, false)
            )
            MESSAGE_FROM -> MessageFromViewHolder(
                inflater.inflate(R.layout.message_from_item, parent, false)
            )
            else -> DateViewHolder(
                inflater.inflate(R.layout.date_item, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MessageToViewHolder -> holder.bind(getItem(position))
            is MessageFromViewHolder -> holder.bind(getItem(position))
            is DateViewHolder -> {
                holder.bind(currentDate)
                return
            }
        }
        holder.itemView.setOnLongClickListener{
            listener.getBottomSheet()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 || currentDate != getItem(position).date) {
            currentDate = getItem(position).date
            return DATE
        }
        return when (getItem(position).userData.id) {
            curId -> MESSAGE_TO
            else -> MESSAGE_FROM
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + dates.size
    }

    override fun getItem(position: Int): MessageData {
        val currentOffset = if (position == 0) {
            0
        } else {
            dates[currentDate] ?: 0
        }
        return super.getItem(position - currentOffset)
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
