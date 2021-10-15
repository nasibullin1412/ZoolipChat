package com.homework.coursework.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.homework.coursework.R
import com.homework.coursework.data.MessageData

class MessageToViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val tvMessageContent: TextView = view.findViewById(R.id.tvMessageContentTo)
    private val cvMessage: CardView = view.findViewById(R.id.cvMessageTo)

    fun bind(messageData: MessageData) {
        cvMessage.setBackgroundResource(R.drawable.bg_custom_message)
        tvMessageContent.text = messageData.messageContent
    }
}
