package com.anhtam.gate9.v2.messenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment

class ChannelActivity : DaggerNavigationFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_channel, container ,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction().replace(R.id.container, ChannelFragment.newInstance()).commit()
    }
}