package com.homework.coursework.presentation.viewholder

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.homework.coursework.R
import com.homework.coursework.presentation.adapter.data.MessageItem
import com.homework.coursework.presentation.customview.CustomFlexboxLayout
import com.homework.coursework.presentation.interfaces.MessageItemCallback
import com.homework.coursework.presentation.utils.emojiLogic

class MessageToViewHolder(private val listener: MessageItemCallback, view: View) :
    RecyclerView.ViewHolder(view) {
    private val tvMessageContent: TextView = view.findViewById(R.id.tvMessageContentTo)
    private val cvMessage: CardView = view.findViewById(R.id.cvMessageTo)
    private val fblEmoji: CustomFlexboxLayout = view.findViewById(R.id.fblEmoji)

    fun bind(messageItem: MessageItem, idx: Int) {
        tvMessageContent.text = messageItem.messageContent
        cvMessage.setBackgroundResource(R.drawable.bg_custom_message)
        fblEmoji.emojiLogic(
            messageItem = messageItem,
            idx = idx,
            listener = listener
        )
    }
}
