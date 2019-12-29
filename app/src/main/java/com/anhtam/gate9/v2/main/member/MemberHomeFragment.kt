package com.anhtam.gate9.v2.main.member

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.anhtam.gate9.adapter.SharePageAdapter
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.charts.ChartScreen
import com.anhtam.gate9.v2.main.member.all.MemberListFragment
import com.anhtam.gate9.utils.customOnClickHolder
import com.anhtam.gate9.utils.debounceClick
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.member_fragment.*
import kotlinx.android.synthetic.main.shared_toolbar_with_back_layout.*

class MemberHomeFragment : DaggerNavigationFragment(), INavigator {

    companion object {
        fun newInstance() = MemberHomeFragment()
    }

    override fun navigateToMemberDetail(idUser: String?, type: String) {
//        UserDiscussionActivity.start(context, idUser?.toInt() ?: 0, Category.Member)
    }

    override fun navigateToMemberFragment() {

    }

    override fun navigateToListFragment(type: String) {
        navigation?.addFragment(MemberListFragment.newInstance(type))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.member_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setUpViewPager()
        initEvents()
    }

    private fun initEvents() {
        backFrameLayout?.customOnClickHolder {
            activity?.onBackPressed()
        }
        llGame?.customOnClickHolder {
            vpTop.currentItem = 0
        }
        llNPH?.customOnClickHolder {
            vpTop.currentItem = 1
        }

        csCharts.debounceClick { navigation?.addFragment(ChartScreen.newInstance()) }
    }

    private fun setUpViewPager(){
        val fragments = arrayListOf<Fragment>()
        fragments.add(MemberFragment.newInstance("member"))
        fragments.add(MemberFragment.newInstance("publisher"))
        vpTop.adapter = SharePageAdapter(childFragmentManager, fragments)
        vpTop.offscreenPageLimit = fragments.size

        vpTop.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

                if(position == 0){
                    vGamer.visibility = View.VISIBLE
                    vNPH.visibility = View.GONE
                } else {
                    vNPH.visibility = View.VISIBLE
                    vGamer.visibility = View.GONE
                }
            }

        })
    }

}