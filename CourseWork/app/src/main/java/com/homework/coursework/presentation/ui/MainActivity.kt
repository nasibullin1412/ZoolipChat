package com.homework.coursework.presentation.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.homework.coursework.R
import com.homework.coursework.presentation.interfaces.BottomNavigationController
import com.homework.coursework.presentation.interfaces.NavigateController
import com.homework.coursework.presentation.utils.CustomFragmentFactory
import com.homework.coursework.presentation.utils.FragmentTag
import com.homework.coursework.presentation.utils.MenuItemIdx
import com.homework.coursework.presentation.utils.addFragment

class MainActivity : AppCompatActivity(), NavigateController, BottomNavigationController {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            val fragmentFactory = CustomFragmentFactory.create(FragmentTag.AUTH_FRAGMENT_TAG)
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
            R.id.streamFragment -> FragmentTag.STREAM_FRAGMENT_TAG
            R.id.profileFragment -> FragmentTag.PROFILE_FRAGMENT_TAG
            R.id.peopleFragment -> FragmentTag.PEOPLE_FRAGMENT_TAG
            else -> throw IllegalArgumentException("Unexpected tag")
        }
        navigateFragment(CustomFragmentFactory.create(tag))
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
        if (bottomNavigationView.visibility == View.GONE) {
            return
        }
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

    override fun goneBottomNavigation() {
        bottomNavigationView.visibility = View.GONE
    }

    override fun visibleBottomNavigation() {
        bottomNavigationView.visibility = View.VISIBLE
    }

    override fun navigateFragment(customFragmentFactory: CustomFragmentFactory) {
        addFragment(customFragmentFactory)
    }
}
