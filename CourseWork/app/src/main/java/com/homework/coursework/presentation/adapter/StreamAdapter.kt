package com.homework.coursework.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.homework.coursework.presentation.ui.stream.main.StreamAllFragment
import com.homework.coursework.presentation.ui.stream.main.StreamSubscribedFragment

class StreamAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = NUMBER_OF_ITEMS

    override fun createFragment(position: Int): Fragment {
        return getNeedFragment(position)
    }

    private fun getNeedFragment(position: Int) = if (position == 0) {
        StreamSubscribedFragment()
    } else {
        StreamAllFragment()
    }

    companion object {
        private const val NUMBER_OF_ITEMS = 2
    }
}
