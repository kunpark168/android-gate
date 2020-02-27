package com.anhtam.gate9.v2.mxh_gate

import android.os.Bundle
import android.view.*
import androidx.viewpager.widget.ViewPager
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.SharePageAdapter
import com.anhtam.gate9.adapter.v2.ArticleAdapter
import com.anhtam.gate9.adapter.v2.du_lieu.HinhAnhAdapter
import com.anhtam.gate9.adapter.v2.du_lieu.VideoAdapter
import com.anhtam.gate9.utils.autoCleared
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.mxh_gate.anh.MXHGateImageScreen
import com.anhtam.gate9.v2.mxh_gate.cam_nang.MXHGateCamNangScreen
import com.anhtam.gate9.v2.mxh_gate.tin_game.MXHGateTinGameScreen
import com.anhtam.gate9.v2.mxh_gate.video.MXHGateVideoScreen
import com.anhtam.gate9.v2.shared.views.AbstractVisibleFragment
import kotlinx.android.synthetic.main.mxh_gate_screen.*

class MXHGateScreen : DaggerNavigationFragment(R.layout.mxh_gate_screen) {

    companion object{
        fun newInstance() = MXHGateScreen()
    }

    private var mAdapter by autoCleared<SharePageAdapter>()

    private val mTabs by lazy { arrayOf(tabAll, tabLove, tabDownload, tabFollowing)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun menuRes() = R.menu.menu_chat_search_more

    private fun init() {
        setUpViewPager()
        initEvents()
    }

    private fun initEvents() {

        for(index in mTabs.toList().indices){
            mTabs[index]?.setOnClickListener {
                enableTab(index)
                vpGate?.currentItem = index
            }
        }
    }

    private fun setUpViewPager() {
        val mFragments = arrayListOf<AbstractVisibleFragment>()
        mFragments.add(MXHGateCamNangScreen(1))
        mFragments.add(MXHGateCamNangScreen(2))
        mFragments.add(MXHGateVideoScreen())
        mFragments.add(MXHGateImageScreen())
        mAdapter = SharePageAdapter(childFragmentManager, mFragments)
        vpGate?.adapter = mAdapter
        vpGate?.offscreenPageLimit = 4

        vpGate?.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                enableTab(position)
                mFragments.forEachIndexed { index, fragment -> fragment.changeVisible(position == index)}
            }
        })
    }

    private fun enableTab(position: Int) {
        for(index in mTabs.toList().indices) {
            mTabs[index]?.setVisibilityIndicator(position == index)
        }
    }

    class EmptyData
}