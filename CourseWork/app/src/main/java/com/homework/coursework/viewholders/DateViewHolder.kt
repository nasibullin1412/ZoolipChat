package com.homework.coursework.viewholders

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.homework.coursework.R
import com.homework.coursework.data.MessageData

class DateViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val tvDate: TextView = view.findViewById(R.id.tvDate)

    fun bind(date:String) {
        tvDate.text = date
    }
}