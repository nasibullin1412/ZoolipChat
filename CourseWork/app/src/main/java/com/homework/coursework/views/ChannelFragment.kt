package com.homework.coursework.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.homework.coursework.R
import com.homework.coursework.adapters.ChannelAdapter
import com.homework.coursework.databinding.ChannelFragmentBinding

class ChannelFragment : Fragment() {

    private var _binding: ChannelFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var channelAdapter: ChannelAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChannelFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        channelAdapter = ChannelAdapter(this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = channelAdapter
        val itemTextList = listOf("Subscribed", "All streams")
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = itemTextList[position]
        }.attach()
    }
}
