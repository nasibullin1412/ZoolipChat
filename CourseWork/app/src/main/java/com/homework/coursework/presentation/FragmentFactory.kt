package com.homework.coursework.presentation

import androidx.fragment.app.Fragment
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.authorization.main.AuthFragment
import com.homework.coursework.presentation.chat.main.TopicChatFragment
import com.homework.coursework.presentation.people.PeopleFragment
import com.homework.coursework.presentation.profile.main.CurrUserProfileFragment
import com.homework.coursework.presentation.profile.main.UserProfileFragment
import com.homework.coursework.presentation.stream.StreamFragment
import com.homework.coursework.presentation.utils.FragmentTag
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class FragmentFactory(var fragment: Fragment, var fragmentTag: FragmentTag) {
    companion object {
        fun create(fragmentTag: FragmentTag): FragmentFactory {
            val fragment = when (fragmentTag) {
                FragmentTag.CHANNEL_FRAGMENT_TAG -> StreamFragment()
                FragmentTag.PROFILE_FRAGMENT_TAG -> CurrUserProfileFragment()
                FragmentTag.PEOPLE_FRAGMENT_TAG -> PeopleFragment()
                FragmentTag.AUTH_FRAGMENT_TAG -> AuthFragment()
                else -> throw IllegalArgumentException("messageFragment without arguments")
            }
            return FragmentFactory(fragment, fragmentTag)
        }

        fun create(
            fragmentTag: FragmentTag,
            topic: TopicItem,
            stream: StreamItem
        ): FragmentFactory {
            val fragment = TopicChatFragment.newInstance(topic, stream)
            return FragmentFactory(fragment, fragmentTag)
        }
    }
}
