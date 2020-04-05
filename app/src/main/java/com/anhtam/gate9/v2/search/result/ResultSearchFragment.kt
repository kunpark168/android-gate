package com.anhtam.gate9.v2.search.result

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.SharePageAdapter
import com.anhtam.gate9.components.custom.BadgeTabItem
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.search.result.article.ArticleSearchResultFragment
import com.anhtam.gate9.v2.search.result.game.GameSearchResultFragment
import com.anhtam.gate9.v2.search.result.member.MemberSearchResultFragment
import com.anhtam.gate9.v2.shared.views.AbstractVisibleFragment
import kotlinx.android.synthetic.main.fragment_result_search.*

class ResultSearchFragment : DaggerNavigationFragment(R.layout.fragment_result_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvents()
    }

    private fun initView() {
        setUpRecyclerView()
    }

    private fun initEvents() {
        tabGame?.setOnClickListener {
            viewPager?.currentItem = 0
            enableTab(tabGame)
        }
        tabMember?.setOnClickListener {
            viewPager?.currentItem = 1
            enableTab(tabMember)
        }
        tabData?.setOnClickListener {
            viewPager?.currentItem = 2
            enableTab(tabData)
        }
    }

    private fun enableTab(tab: BadgeTabItem) {
        tabGame?.enableState(tab == tabGame)
        tabMember?.enableState(tab == tabMember)
        tabData?.enableState(tab == tabData)
    }

    private fun setUpRecyclerView() {
        val fragments = arrayListOf<AbstractVisibleFragment>()
        fragments.add(GameSearchResultFragment())
        fragments.add(MemberSearchResultFragment())
        fragments.add(ArticleSearchResultFragment())
        childFragmentManager.run {
            val adapter = SharePageAdapter(this, fragments)
            viewPager?.adapter = adapter
            viewPager?.offscreenPageLimit = 2
        }
        viewPager?.addOnPageChangeListener(object:ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> enableTab(tabGame)
                    1 -> enableTab(tabMember)
                    2 -> enableTab(tabData)
                    else -> {}
                }
            }

        })
    }

    companion object {
        fun newInstance(): ResultSearchFragment {
            return ResultSearchFragment()
        }
    }
}
