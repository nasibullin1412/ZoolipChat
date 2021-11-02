package com.homework.coursework.presentation.stream

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.homework.coursework.R
import com.homework.coursework.databinding.StreamFragmentBinding
import com.homework.coursework.presentation.adapter.StreamAdapter
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.interfaces.AddTopicDiscussion
import com.homework.coursework.presentation.interfaces.BottomNavigationController
import com.homework.coursework.presentation.stream.StreamListFragment.Companion.REQUEST_KEY_CHOICE
import com.homework.coursework.presentation.stream.StreamListFragment.Companion.STREAM_KEY
import com.homework.coursework.presentation.stream.StreamListFragment.Companion.TOPIC_KEY

class StreamFragment : Fragment() {

    private var _binding: StreamFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var streamAdapter: StreamAdapter
    private lateinit var viewPager: ViewPager2
    private var topicItemCallback: AddTopicDiscussion? = null
    private var bottomNavigationController: BottomNavigationController? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AddTopicDiscussion) {
            topicItemCallback = context
        }
        if (context is BottomNavigationController) {
            bottomNavigationController = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.setFragmentResultListener(
            REQUEST_KEY_CHOICE,
            this
        ) { _, bundle ->
            val stream = bundle.getParcelable<StreamItem>(STREAM_KEY)
            val topic = bundle.getParcelable<TopicItem>(TOPIC_KEY)
            topicItemCallback?.addTopicDiscussion(
                topic = topic,
                stream = stream
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StreamFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        streamAdapter = StreamAdapter(this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = streamAdapter
        val itemTextList = resources.getStringArray(R.array.view_pager_items)
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = itemTextList[position]
        }.attach()
        bottomNavigationController?.visibleBottomNavigation()
        initSearchEditText()
    }

    private fun initSearchEditText() {
        binding.etSearch.doAfterTextChanged {
            val position = binding.pager.currentItem
            childFragmentManager.setFragmentResult(
                "$KEY_QUERY_REQUEST$position",
                bundleOf(KEY_QUERY_DATA to it.toString())
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        topicItemCallback = null
    }

    companion object {
        const val KEY_QUERY_REQUEST = "queryRequest"
        const val KEY_QUERY_DATA = "queryData"
    }
}
