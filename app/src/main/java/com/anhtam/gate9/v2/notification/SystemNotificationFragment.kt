package com.anhtam.gate9.v2.notification


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment

class SystemNotificationFragment : DaggerNavigationFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_system_notification, container, false)
    }

    companion object {
        fun newInstance(): SystemNotificationFragment {
            return SystemNotificationFragment()
        }
    }
}
