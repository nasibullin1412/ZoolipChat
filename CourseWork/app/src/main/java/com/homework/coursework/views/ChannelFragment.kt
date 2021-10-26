package com.homework.coursework.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.homework.coursework.R
import com.homework.coursework.adapters.ChannelAdapter
import com.homework.coursework.databinding.ChannelFragmentBinding
import com.homework.coursework.interfaces.AddTopicDiscussion
import com.homework.coursework.interfaces.BottomNavigationController
import com.homework.coursework.views.ChannelListFragment.Companion.CHANNEL_KEY
import com.homework.coursework.views.ChannelListFragment.Companion.TOPIC_KEY

class ChannelFragment : Fragment() {

    private var _binding: ChannelFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var channelAdapter: ChannelAdapter
    private lateinit var viewPager: ViewPager2
    private var topicItemCallback: AddTopicDiscussion? = null
    private var bottomNavigationController: BottomNavigationController? = null

    /**
     * signature of the activity as a listener
     * */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AddTopicDiscussion) {
            topicItemCallback = context
        }
        if (context is BottomNavigationController){
            bottomNavigationController = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.setFragmentResultListener(
            "requestKey",
            this
        ) { _, bundle ->
            val channelId = bundle.getInt(CHANNEL_KEY)
            val topicId = bundle.getInt(TOPIC_KEY)
            topicItemCallback?.addTopicDiscussion(
                idTopic = topicId,
                idChannel = channelId
            )
        }
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
        bottomNavigationController?.visibleBottomNavigation()
    }

    override fun onDetach() {
        super.onDetach()
        topicItemCallback = null
    }
}
