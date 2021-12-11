package com.homework.coursework.presentation.utils

import androidx.fragment.app.Fragment
import com.homework.coursework.presentation.adapter.data.StreamItem
import com.homework.coursework.presentation.adapter.data.TopicItem
import com.homework.coursework.presentation.ui.authorization.main.AuthFragment
import com.homework.coursework.presentation.ui.chat.main.TopicChatFragment
import com.homework.coursework.presentation.ui.createstream.main.CreateStreamFragment
import com.homework.coursework.presentation.ui.people.PeopleFragment
import com.homework.coursework.presentation.ui.profile.main.CurrUserProfileFragment
import com.homework.coursework.presentation.ui.profile.main.UserProfileFragment
import com.homework.coursework.presentation.ui.stream.StreamFragment

class CustomFragmentFactory(var fragment: Fragment, var fragmentTag: FragmentTag) {
    companion object {
        fun create(fragmentTag: FragmentTag): CustomFragmentFactory {
            val fragment = when (fragmentTag) {
                FragmentTag.CHANNEL_FRAGMENT_TAG -> StreamFragment()
                FragmentTag.PROFILE_FRAGMENT_TAG -> CurrUserProfileFragment()
                FragmentTag.PEOPLE_FRAGMENT_TAG -> PeopleFragment()
                FragmentTag.AUTH_FRAGMENT_TAG -> AuthFragment()
                FragmentTag.CREATE_STREAM_FRAGMENT_TAG -> CreateStreamFragment()
                else -> throw IllegalArgumentException("messageFragment without arguments")
            }
            return CustomFragmentFactory(fragment, fragmentTag)
        }

        fun create(
            fragmentTag: FragmentTag,
            topic: TopicItem,
            stream: StreamItem
        ): CustomFragmentFactory {
            val fragment = TopicChatFragment.newInstance(topic, stream)
            return CustomFragmentFactory(fragment, fragmentTag)
        }

        fun create(
            fragmentTag: FragmentTag,
            userId: Int
        ): CustomFragmentFactory {
            val fragment = UserProfileFragment.newInstance(userId)
            return CustomFragmentFactory(fragment, fragmentTag)
        }
    }
}
