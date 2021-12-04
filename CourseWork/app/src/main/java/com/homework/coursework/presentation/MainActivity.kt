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
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MainActivity : AppCompatActivity(), NavigateController, BottomNavigationController {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            val fragmentFactory = FragmentFactory.create(FragmentTag.AUTH_FRAGMENT_TAG)
            addFragment(fragmentFactory)
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
        val fragmentFactory = FragmentFactory.create(tag)
        addFragment(fragmentFactory)
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

    override fun navigateFragment(topic: TopicItem, stream: StreamItem) {
        val fragmentFactory = FragmentFactory.create(
            fragmentTag = FragmentTag.TOPIC_DISCUSSION_FRAGMENT_TAG,
            topic = topic,
            stream = stream
        )
        addFragment(fragmentFactory)
    }

    override fun navigateFragment() {
        val fragmentFactory = FragmentFactory.create(FragmentTag.CHANNEL_FRAGMENT_TAG)
        addFragment(fragmentFactory)
    }

    override fun logoutApp() {
        val fragmentFactory = FragmentFactory.create(FragmentTag.AUTH_FRAGMENT_TAG)
        addFragment(fragmentFactory)
    }

    override fun goneBottomNavigation() {
        bottomNavigationView.visibility = View.GONE
    }

    override fun visibleBottomNavigation() {
        bottomNavigationView.visibility = View.VISIBLE
    }
}
