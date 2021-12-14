package com.homework.coursework.presentation.viewholder.chat

import com.homework.coursework.databinding.TopicChatItemBinding

class TopicNameViewHolder(private val viewBinding: TopicChatItemBinding) :
    ChatViewHolder(viewBinding.root) {

    fun bind(topicName: String) {
        viewBinding.tvTopicName.text = topicName
    }
}
