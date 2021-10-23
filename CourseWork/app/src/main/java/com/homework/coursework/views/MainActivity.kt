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

}
