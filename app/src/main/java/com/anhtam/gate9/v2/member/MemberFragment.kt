package com.anhtam.gate9.v2.member

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.SharePageAdapter
import com.anhtam.gate9.utils.customOnClickHolder
import com.anhtam.gate9.v2.bxh.BXHScreen
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.member.all.MemberListFragment
import com.anhtam.gate9.vo.EUser
import kotlinx.android.synthetic.main.member_fragment.*

class MemberFragment : DaggerNavigationFragment(R.layout.member_fragment), INavigator {

    companion object {
        fun newInstance() = MemberFragment()
    }

    override fun navigateToMemberDetail(idUser: String?, type: String) {
//        UserDiscussionScreen.start(context, idUser?.toInt() ?: 0, Category.Member)
    }

    override fun navigateToMemberFragment() {

    }

    override fun navigateToListFragment(type: EUser) {
        navigation?.addFragment(MemberListFragment.newInstance(type))
    }

    override fun menuRes() = R.menu.menu_search_avatar_more

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setUpViewPager()
        initEvents()
    }

    private fun initEvents() {
        llGame?.customOnClickHolder {
            vpTop.currentItem = 0
        }
        llNPH?.customOnClickHolder {
            vpTop.currentItem = 1
        }

        topBXHUserLayout.setOnClickListener {
            navigation?.addFragment(BXHScreen.newInstance(4))
        }

        topBXHNPHLayout.setOnClickListener {
            navigation?.addFragment(BXHScreen.newInstance(5))
        }

    }

    private fun setUpViewPager(){
        val fragments = arrayListOf<Fragment>()
        fragments.add(MemberTabFragment.newInstance(EUser.TV))
        fragments.add(MemberTabFragment.newInstance(EUser.NPH))
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