package com.homework.coursework.presentation.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.homework.coursework.R
import com.homework.coursework.presentation.FragmentFactory
import kotlinx.serialization.ExperimentalSerializationApi

fun AppCompatActivity.addFragmentWithoutBackstack(fragmentFactory: FragmentFactory, tag: String) {
    if (supportFragmentManager.backStackEntryCount != 0) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
    supportFragmentManager.beginTransaction()
        .replace(R.id.nav_host_fragment, fragmentFactory.fragment, tag)
        .commit()
}

@ExperimentalSerializationApi
fun AppCompatActivity.addFragment(fragmentFactory: FragmentFactory) {
    val tag = fragmentFactory.fragmentTag.value
    if (fragmentFactory.fragmentTag == FragmentTag.CHANNEL_FRAGMENT_TAG) {
        addFragmentWithoutBackstack(fragmentFactory = fragmentFactory, tag = tag)
        return
    }
    if (fragmentFactory.fragmentTag == FragmentTag.AUTH_FRAGMENT_TAG) {
        addFragmentWithoutBackstack(fragmentFactory = fragmentFactory, tag = tag)
        return
    }
    supportFragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    supportFragmentManager.beginTransaction()
        .replace(R.id.nav_host_fragment, fragmentFactory.fragment, tag)
        .addToBackStack(tag)
        .commit()
}
