package com.homework.coursework.presentation.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.homework.coursework.presentation.ui.authorization.main.AuthFragment
import com.homework.coursework.presentation.ui.chat.main.StreamChatFragment
import com.homework.coursework.presentation.ui.chat.main.TopicChatFragment
import com.homework.coursework.presentation.ui.createstream.main.CreateStreamFragment
import com.homework.coursework.presentation.ui.people.PeopleFragment
import com.homework.coursework.presentation.ui.profile.main.CurrUserProfileFragment
import com.homework.coursework.presentation.ui.profile.main.UserProfileFragment
import com.homework.coursework.presentation.ui.stream.StreamFragment

class CustomFragmentFactory(var fragment: Fragment, var fragmentTag: FragmentTag) {
    companion object {
        fun create(fragmentTag: FragmentTag, bundle: Bundle = Bundle()): CustomFragmentFactory {
            val fragment = when (fragmentTag) {
                FragmentTag.STREAM_FRAGMENT_TAG -> StreamFragment()
                FragmentTag.PROFILE_FRAGMENT_TAG -> CurrUserProfileFragment()
                FragmentTag.PEOPLE_FRAGMENT_TAG -> PeopleFragment()
                FragmentTag.AUTH_FRAGMENT_TAG -> AuthFragment()
                FragmentTag.CREATE_STREAM_FRAGMENT_TAG -> CreateStreamFragment()
                FragmentTag.USER_PROFILE_FRAGMENT_TAG -> UserProfileFragment.newInstance(bundle)
                FragmentTag.TOPIC_CHAT_FRAGMENT_TAG -> TopicChatFragment.newInstance(bundle)
                FragmentTag.STREAM_CHAT_FRAGMENT_TAG -> StreamChatFragment.newInstance(bundle)
            }
            return CustomFragmentFactory(fragment, fragmentTag)
        }
    }
}
