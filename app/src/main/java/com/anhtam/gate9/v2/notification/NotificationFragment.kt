package com.anhtam.gate9.v2.notification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.SharePageAdapter
import com.anhtam.gate9.utils.debounceClick
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.shared.NotificationTabItem
import kotlinx.android.synthetic.main.notification_activity.*

class NotificationFragment : DaggerNavigationFragment(R.layout.notification_activity) {

    companion object {
        fun newInstance() = NotificationFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvents()
    }

    private fun initView() {
        toolbar?.title = resources.getString(R.string.notify)
        setUpViewPager()
    }

    private fun initEvents() {
        tabNotify.debounceClick {
            vpNotification?.currentItem = 0
        }
        tabSystem.debounceClick {
            vpNotification?.currentItem = 1
        }
        vpNotification?.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> enableTab(tabNotify)
                    1 -> enableTab(tabSystem)
                    else -> {}
                }
            }

        })
    }

    private fun enableTab(tab: NotificationTabItem) {
        tabNotify.setVisibilityIndicator(tab === tabNotify)
        tabSystem.setVisibilityIndicator(tab === tabSystem)
    }

    private fun setUpViewPager() {
        val fragments = ArrayList<Fragment>()
        fragments.add(AllNotificationFragment.newInstance())
        fragments.add(SystemNotificationFragment.newInstance())
        val mAdapter = SharePageAdapter(childFragmentManager, fragments)
        vpNotification.adapter = mAdapter
        vpNotification.offscreenPageLimit = 2
    }

}