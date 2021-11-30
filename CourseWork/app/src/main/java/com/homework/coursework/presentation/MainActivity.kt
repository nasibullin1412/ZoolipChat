package com.homework.coursework.presentation

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.homework.coursework.R
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.interfaces.BottomNavigationController
import com.homework.coursework.presentation.interfaces.NavigateController
import com.homework.coursework.presentation.utils.FragmentTag
import com.homework.coursework.presentation.utils.MenuItemIdx
import com.homework.coursework.presentation.utils.addFragment
import com.homework.coursework.presentation.utils.fragmentByTag
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MainActivity : AppCompatActivity(), NavigateController, BottomNavigationController {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            val tag = FragmentTag.AUTH_FRAGMENT_TAG
            addFragment(tag.fragmentByTag(), tag)
        }
        initNavigationListener()
    }

    private fun initNavigationListener() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar)
        bottomNavigationView.setOnItemSelectedListener {
            actionBottom(it)
        }
    }

    /**
     * Bottom navigation listener action
     * @param item new selected item
     */
    private fun actionBottom(item: MenuItem): Boolean {
        val tag = when (item.itemId) {
            R.id.channelFragment -> FragmentTag.CHANNEL_FRAGMENT_TAG
            R.id.profileFragment -> FragmentTag.PROFILE_FRAGMENT_TAG
            R.id.peopleFragment -> FragmentTag.PEOPLE_FRAGMENT_TAG
            else -> throw IllegalArgumentException("Unexpected tag")
        }
        addFragment(tag.fragmentByTag(), tag)
        return true
    }

    override fun onBackPressed() {
        setPreviousItem()
        super.onBackPressed()
    }

    /**
     * set correct checked item for bottom navigation after back press
     */
    private fun setPreviousItem() {
        if (supportFragmentManager.backStackEntryCount <= 1) {
            bottomNavigationView.menu.getItem(MenuItemIdx.CHANNEL_IDX.value).isChecked = true
            return
        }
        if (bottomNavigationView.selectedItemId == R.id.profileFragment) {
            bottomNavigationView.menu.getItem(MenuItemIdx.PEOPLE_IDX.value).isChecked = true
            return
        }
        bottomNavigationView.menu.getItem(MenuItemIdx.PROFILE_IDX.value).isChecked = true
    }

    override fun navigateFragment(topic: TopicItem?, stream: StreamItem?) {
        val tag = FragmentTag.TOPIC_DISCUSSION_FRAGMENT_TAG
        addFragment(tag.fragmentByTag(topic = topic, stream = stream), tag)
    }

    override fun navigateFragment() {
        val tag = FragmentTag.CHANNEL_FRAGMENT_TAG
        addFragment(tag.fragmentByTag(), tag)
    }

    override fun goneBottomNavigation() {
        bottomNavigationView.visibility = View.GONE
    }

    override fun visibleBottomNavigation() {
        bottomNavigationView.visibility = View.VISIBLE
    }
}
