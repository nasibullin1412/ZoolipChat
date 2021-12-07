package com.homework.coursework.presentation.ui.stream

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
import com.homework.coursework.presentation.App
import com.homework.coursework.presentation.utils.SearchListener
import com.homework.coursework.presentation.adapter.StreamAdapter
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.interfaces.NavigateController
import com.homework.coursework.presentation.ui.stream.StreamItemBaseFragment.Companion.REQUEST_KEY_CHOICE
import com.homework.coursework.presentation.ui.stream.StreamItemBaseFragment.Companion.STREAM_KEY
import com.homework.coursework.presentation.ui.stream.StreamItemBaseFragment.Companion.TOPIC_KEY
import com.homework.coursework.presentation.utils.showToast
import javax.inject.Inject

class StreamFragment : Fragment() {

    @Inject
    internal lateinit var searchListener: SearchListener

    private var _binding: StreamFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var streamAdapter: StreamAdapter
    private lateinit var viewPager: ViewPager2
    private var topicItemCallback: NavigateController? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigateController) {
            topicItemCallback = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.setFragmentResultListener(
            REQUEST_KEY_CHOICE,
            this
        ) { _, bundle ->
            val stream = bundle.getParcelable<StreamItem>(STREAM_KEY)
                ?: throw IllegalArgumentException("Stream required")
            val topic = bundle.getParcelable<TopicItem>(TOPIC_KEY)
                ?: throw IllegalArgumentException("Topic required")
            topicItemCallback?.navigateFragment(
                topic = topic,
                stream = stream
            )
        }
        App.appComponent.streamComponent().inject(this)
        searchListener.subscribeToSearchSubject({ setQuery(it) }, { showToast(it) })
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
        initSearchEditText()
    }

    private fun initSearchEditText() {
        binding.etSearch.doAfterTextChanged {
            searchListener.searchTopics(it.toString())
        }
    }

    private fun setQuery(query: String) {
        val position = binding.pager.currentItem
        childFragmentManager.setFragmentResult(
            "$KEY_QUERY_REQUEST$position",
            bundleOf(KEY_QUERY_DATA to query)
        )
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
