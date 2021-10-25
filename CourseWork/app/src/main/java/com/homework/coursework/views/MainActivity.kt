package com.homework.coursework.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.homework.coursework.R
import com.homework.coursework.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        supportFragmentManager.beginTransaction()
            .add(R.id.nav_host_fragment, ChannelFragment(), "Disc")
            .commit()
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
