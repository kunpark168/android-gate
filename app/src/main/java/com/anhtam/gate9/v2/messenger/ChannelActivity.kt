package com.anhtam.gate9.v2.messenger

import android.os.Bundle
import android.view.View
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment

class ChannelActivity : DaggerNavigationFragment(R.layout.activity_channel){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction().replace(R.id.container, ChannelFragment.newInstance()).commit()
    }
}