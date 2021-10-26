package com.homework.coursework.views

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.homework.coursework.R
import com.homework.coursework.data.FragmentTag
import com.homework.coursework.data.MenuItemIdx
import com.homework.coursework.interfaces.AddTopicDiscussion
import com.homework.coursework.interfaces.BottomNavigationController
import com.homework.coursework.utils.addFragment
import com.homework.coursework.utils.fragmentByTag

class MainActivity : AppCompatActivity(), AddTopicDiscussion, BottomNavigationController {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            val tag = FragmentTag.CHANNEL_FRAGMENT_TAG
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

    override fun addTopicDiscussion(idTopic: Int, idChannel: Int) {
        val tag = FragmentTag.TOPIC_DISCUSSION_FRAGMENT_TAG
        addFragment(tag.fragmentByTag(idTopic = idTopic, idChannel = idChannel), tag)
    }

    override fun goneBottomNavigation() {
        bottomNavigationView.visibility = View.GONE
    }

    override fun visibleBottomNavigation() {
        bottomNavigationView.visibility = View.VISIBLE
    }

    companion object {
        const val DEFAULT_MESSAGE_ID = -1
        const val CURR_USER_ID = 1
        const val CURR_USER_NAME = "Марк Цукерберг"
        const val CURR_USER_AVATAR_URL = "https://clck.ru/YDyYU"
        const val CURR_USER_DATE = "3 фев"
        const val CURR_USER_STATUS = "In a meeting"
        const val CURR_USER_STATE = "online"
    }
}
