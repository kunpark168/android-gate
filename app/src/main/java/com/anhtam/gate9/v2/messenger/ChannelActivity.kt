package com.anhtam.gate9.v2.messenger

import android.os.Bundle
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.navigation.NavigationDispatcher
import com.anhtam.gate9.navigation.NavigationProvider
import com.anhtam.gate9.ui.base.BaseActivity

class ChannelActivity : BaseActivity(), NavigationProvider{
    override fun provideNavigation(): Navigation {
        return NavigationDispatcher(this, R.id.container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel)
        supportFragmentManager.beginTransaction().replace(R.id.container, ChannelFragment.newInstance()).commit()

    }
}