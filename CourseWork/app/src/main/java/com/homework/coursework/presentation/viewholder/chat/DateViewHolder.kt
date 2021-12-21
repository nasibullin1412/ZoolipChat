package com.homework.coursework.presentation.viewholder.chat

import com.homework.coursework.databinding.ItemDateBinding

class DateViewHolder(private val viewBinding: ItemDateBinding) : ChatViewHolder(viewBinding.root) {

    fun bind(date: String) {
        viewBinding.tvDate.text = date
    }
}
