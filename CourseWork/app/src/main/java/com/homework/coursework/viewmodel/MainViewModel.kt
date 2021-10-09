package com.homework.coursework.viewmodel

import androidx.lifecycle.ViewModel
import com.homework.coursework.customview.CustomEmojiView

class MainViewModel: ViewModel() {
    val customEmojiViews :ArrayList<CustomEmojiView> by lazy {
        ArrayList()
    }
}