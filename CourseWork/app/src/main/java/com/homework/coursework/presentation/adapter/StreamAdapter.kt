package com.homework.coursework.presentation.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.homework.coursework.presentation.stream.StreamListFragment
import com.homework.coursework.presentation.stream.StreamListFragment.Companion.ARG_OBJECT

class StreamAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = NUMBER_OF_ITEMS

    override fun createFragment(position: Int): Fragment {
        val fragment = StreamListFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT, position)
        }
        return fragment
    }

    companion object {
        private const val NUMBER_OF_ITEMS = 2
    }
}
