package com.homework.coursework.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.homework.coursework.R

class DateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvDate: TextView = view.findViewById(R.id.tvDate)

    fun bind(date: String) {
        tvDate.text = date
    }
}
