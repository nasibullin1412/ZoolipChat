package com.homework.coursework.presentation.ui.chat.utils

import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import com.homework.coursework.R
import com.homework.coursework.presentation.ui.chat.ChatBaseFragment


internal fun ChatBaseFragment.initEditText() = with(binding) {
    imgSend.background =
        ResourcesCompat.getDrawable(resources, R.drawable.ic_vector, context?.theme)
    etMessage.run {
        addTextChangedListener {
            binding.imgSend.background = ResourcesCompat.getDrawable(
                resources,
                selectIcon(text.toString()),
                context.theme
            )
        }
    }
}

internal fun selectIcon(text: String): Int = if (text.isEmpty()) {
    R.drawable.ic_vector
} else {
    R.drawable.ic_vector_send
}
