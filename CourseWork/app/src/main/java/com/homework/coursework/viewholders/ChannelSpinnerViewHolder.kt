package com.homework.coursework.viewholders

import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.homework.coursework.R
import com.homework.coursework.data.ChannelData

class ChannelSpinnerViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val spinner = view.findViewById<Spinner>(R.id.spAction)

    fun bind(filteredActionData: ChannelData) {
        val spinAdapter = ArrayAdapter(
            view.context,
            R.layout.spinner,
            listOf(
                filteredActionData.channelName,
                filteredActionData
            )
        )
        spinner.adapter = spinAdapter
    }
}
