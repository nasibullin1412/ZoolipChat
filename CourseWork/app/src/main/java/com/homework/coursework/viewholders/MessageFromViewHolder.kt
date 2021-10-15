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

class MessageFromViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val imgAvatar: ImageView = view.findViewById(R.id.imgUserAvatar)
    private val tvName: TextView = view.findViewById(R.id.tvUserName)
    private val tvMessageContent: TextView = view.findViewById(R.id.tvMessageContent)
    private val cvMessage: CardView = view.findViewById(R.id.cvMessageFrom)

    fun bind(messageData: MessageData) {
        imgAvatar.load(messageData.userData.avatarUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        cvMessage.setBackgroundResource(R.drawable.bg_custom_message)
        tvName.text = messageData.userData.name
        tvMessageContent.text = messageData.messageContent
    }
}
