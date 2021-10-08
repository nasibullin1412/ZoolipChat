package com.homework.coursework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.homework.coursework.customview.CustomEmojiView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_main)
        val customEmojiView = findViewById<CustomEmojiView>(R.id.customEmojiView)
        customEmojiView.setOnClickListener{
            customEmojiView.isSelected = customEmojiView.isSelected.not()
        }
    }
}