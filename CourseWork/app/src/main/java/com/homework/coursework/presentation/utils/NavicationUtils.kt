package com.homework.coursework.presentation.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.homework.coursework.R

fun AppCompatActivity.addFragmentWithoutBackstack(customFragmentFactory: CustomFragmentFactory, tag: String) {
    if (supportFragmentManager.backStackEntryCount != 0) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
    supportFragmentManager.beginTransaction()
        .replace(R.id.nav_host_fragment, customFragmentFactory.fragment, tag)
        .commit()
}

fun AppCompatActivity.addFragment(customFragmentFactory: CustomFragmentFactory) {
    val tag = customFragmentFactory.fragmentTag.value
    if (customFragmentFactory.fragmentTag == FragmentTag.CHANNEL_FRAGMENT_TAG) {
        addFragmentWithoutBackstack(customFragmentFactory = customFragmentFactory, tag = tag)
        return
    }
    if (customFragmentFactory.fragmentTag == FragmentTag.AUTH_FRAGMENT_TAG) {
        addFragmentWithoutBackstack(customFragmentFactory = customFragmentFactory, tag = tag)
        return
    }
    supportFragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    supportFragmentManager.beginTransaction()
        .replace(R.id.nav_host_fragment, customFragmentFactory.fragment, tag)
        .addToBackStack(tag)
        .commit()
}
