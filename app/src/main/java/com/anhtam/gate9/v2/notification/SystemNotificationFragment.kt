package com.anhtam.gate9.v2.notification

import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment

class SystemNotificationFragment : DaggerNavigationFragment(R.layout.fragment_system_notification) {

    companion object {
        fun newInstance(): SystemNotificationFragment {
            return SystemNotificationFragment()
        }
    }
}
