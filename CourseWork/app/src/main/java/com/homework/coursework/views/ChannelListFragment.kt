package com.homework.coursework.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.homework.coursework.R
import com.homework.coursework.adapters.ChannelNameAdapter
import com.homework.coursework.channelListData
import com.homework.coursework.data.ChannelData
import com.homework.coursework.databinding.ChannelViewPageFragmentBinding
import com.homework.coursework.viewmodel.ChannelViewModel

class ChannelListFragment : Fragment() {
    private lateinit var recycleList: ArrayList<ChannelData>
    private lateinit var channelAdapter: ChannelNameAdapter
    private var _binding: ChannelViewPageFragmentBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException(
                "Cannot access view in after view destroyed and before view creation"
            )

    private var isInAction = false
    private val viewModel: ChannelViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChannelViewPageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgument()
        initRecycler()
        initObserver()
    }

    private fun initObserver() {
        viewModel.query.observe(
            viewLifecycleOwner, {
                dataUpdate(channelListData)
            }
        )
    }

    private fun dataUpdate(listChannels: ArrayList<ChannelData>) {
        binding.rvSpinner.visibility = View.VISIBLE
        recycleList = listChannels
        channelAdapter.submitList(recycleList)
    }

    private fun initRecycler() {
        channelAdapter = ChannelNameAdapter()
        with(binding.rvSpinner) {
            val itemDecorator = DividerItemDecoration(
                binding.rvSpinner.context,
                DividerItemDecoration.VERTICAL
            ).apply {
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.sh_item_border,
                    context.theme
                )?.let { setDrawable(it) }
            }
            addItemDecoration(itemDecorator)
            adapter = channelAdapter
            layoutManager = LinearLayoutManager(context)
        }
        dataUpdate(channelListData)
    }

    private fun getArgument() {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            isInAction = getInt(ARG_OBJECT) == SEARCH_IN_ACTION
        }
    }

    companion object {
        const val ARG_OBJECT = "object"
        const val SEARCH_IN_ACTION = 0
    }
}
