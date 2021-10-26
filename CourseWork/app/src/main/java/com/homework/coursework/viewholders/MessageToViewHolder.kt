package com.homework.coursework.viewholders

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.homework.coursework.R
import com.homework.coursework.customview.CustomFlexboxLayout
import com.homework.coursework.data.MessageData
import com.homework.coursework.interfaces.MessageItemCallback
import com.homework.coursework.utils.emojiLogic

class MessageToViewHolder(private val listener: MessageItemCallback, view: View) :
    RecyclerView.ViewHolder(view) {
    private val tvMessageContent: TextView = view.findViewById(R.id.tvMessageContentTo)
    private val cvMessage: CardView = view.findViewById(R.id.cvMessageTo)
    private val fblEmoji: CustomFlexboxLayout = view.findViewById(R.id.fblEmoji)

    fun bind(messageData: MessageData, idx: Int) {
        tvMessageContent.text = messageData.messageContent
        cvMessage.setBackgroundResource(R.drawable.bg_custom_message)
        fblEmoji.emojiLogic(
            messageData = messageData,
            idx = idx,
            listener = listener
        )
    }
}
