package com.homework.coursework.presentation.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.homework.coursework.R

fun AppCompatActivity.addFragmentWithoutBackstack(customFragmentFactory: CustomFragmentFactory) {
    if (supportFragmentManager.backStackEntryCount != 0) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
    supportFragmentManager.beginTransaction()
        .replace(
            R.id.nav_host_fragment,
            customFragmentFactory.fragment,
            customFragmentFactory.fragmentTag.value
        )
        .commit()
}

fun AppCompatActivity.addFragment(customFragmentFactory: CustomFragmentFactory) {
    val tag = customFragmentFactory.fragmentTag
    if (tag == FragmentTag.STREAM_FRAGMENT_TAG) {
        addFragmentWithoutBackstack(customFragmentFactory = customFragmentFactory)
        return
    }
    if (customFragmentFactory.fragmentTag == FragmentTag.AUTH_FRAGMENT_TAG) {
        addFragmentWithoutBackstack(customFragmentFactory = customFragmentFactory)
        return
    }
    supportFragmentManager.popBackStack(tag.value, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    supportFragmentManager.beginTransaction()
        .replace(R.id.nav_host_fragment, customFragmentFactory.fragment, tag.value)
        .addToBackStack(tag.value)
        .commit()
}
