package com.homework.coursework.presentation.viewholder.chat

import com.homework.coursework.databinding.ItemTopicChatBinding

class TopicNameViewHolder(private val viewBinding: ItemTopicChatBinding) :
    ChatViewHolder(viewBinding.root) {

    fun bind(topicName: String) {
        viewBinding.tvTopicName.text = topicName
    }
}
