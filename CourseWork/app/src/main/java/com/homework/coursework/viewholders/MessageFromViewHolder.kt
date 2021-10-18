package com.homework.coursework.viewholders

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.imageview.ShapeableImageView
import com.homework.coursework.R
import com.homework.coursework.customview.CustomEmojiView
import com.homework.coursework.customview.CustomFlexboxLayout
import com.homework.coursework.data.EmojiData
import com.homework.coursework.data.MessageData
import com.homework.coursework.interfaces.MessageItemCallback
import com.homework.coursework.utils.*

class MessageFromViewHolder(private val listener: MessageItemCallback, view: View) :
    RecyclerView.ViewHolder(view) {
    private val imgAvatar: ImageView = view.findViewById(R.id.imgUserAvatar)
    private val tvName: TextView = view.findViewById(R.id.tvUserName)
    private val tvMessageContent: TextView = view.findViewById(R.id.tvMessageContent)
    private val cvMessage: CardView = view.findViewById(R.id.cvMessageFrom)
    private val fblEmoji: CustomFlexboxLayout = view.findViewById(R.id.fblEmoji)

    fun bind(messageData: MessageData, idx: Int) {
        imgAvatar.load(messageData.userData.avatarUrl) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        cvMessage.setBackgroundResource(R.drawable.bg_custom_message)
        tvName.text = messageData.userData.name
        tvMessageContent.text = messageData.messageContent
        fblEmoji.emojiLogic(
            messageData = messageData,
            idx = idx,
            listener = listener
        )
    }
}
