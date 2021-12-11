package com.homework.coursework.presentation.viewholder.chat

import com.homework.coursework.databinding.DateItemBinding

class DateViewHolder(private val viewBinding: DateItemBinding) : ChatViewHolder(viewBinding.root) {

    fun bind(date: String) {
        viewBinding.tvDate.text = date
    }
}
