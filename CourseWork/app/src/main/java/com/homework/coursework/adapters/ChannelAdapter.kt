package com.homework.coursework.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.homework.coursework.views.ChannelListFragment
import com.homework.coursework.views.ChannelListFragment.Companion.ARG_OBJECT

class ChannelAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = NUMBER_OF_ITEMS

    override fun createFragment(position: Int): Fragment {
        val fragment = ChannelListFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT, position)
        }
        return fragment
    }

    companion object {
        private const val NUMBER_OF_ITEMS = 2
    }
}
