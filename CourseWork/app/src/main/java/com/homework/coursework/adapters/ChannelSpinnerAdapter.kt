package com.homework.coursework.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.homework.coursework.R
import com.homework.coursework.callbacks.ChannelCallback
import com.homework.coursework.data.ChannelData
import com.homework.coursework.viewholders.ChannelSpinnerViewHolder

class ChannelSpinnerAdapter : ListAdapter<ChannelData,
        ChannelSpinnerViewHolder>(ChannelCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelSpinnerViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return ChannelSpinnerViewHolder(
            inflater.inflate(R.layout.spinner_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChannelSpinnerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
