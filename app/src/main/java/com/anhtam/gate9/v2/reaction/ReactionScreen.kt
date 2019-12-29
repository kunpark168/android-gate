package com.anhtam.gate9.v2.reaction

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.anhtam.gate9.adapter.SharePageAdapter
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.debounceClick
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.reaction_screen.*
import java.util.*

class ReactionScreen : DaggerNavigationFragment() {

    companion object {
        fun newInstance() = ReactionScreen()
    }

    private lateinit var mAdapter: SharePageAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.reaction_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setSupportActionBar(toolbar)
        initViewPager()
        initControl()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_chat_search_more, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initViewPager(){
        val fragments = ArrayList<Fragment>()
        mAdapter = SharePageAdapter(childFragmentManager, fragments)
        vpReaction.adapter = mAdapter
        vpReaction.offscreenPageLimit = 5

        vpReaction.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                onTabSelected(position)
            }

        })
    }

    private fun initControl(){
        backFrameLayout?.debounceClick { navigation?.back() }

        csShare.debounceClick { vpReaction.currentItem = 0 }
        csLove.debounceClick { vpReaction.currentItem = 1 }
        csLike.debounceClick { vpReaction.currentItem = 2 }
        csDisLike.debounceClick { vpReaction.currentItem = 3 }
        csComment.debounceClick { vpReaction.currentItem = 4 }
    }

    private fun onTabSelected(tab: Int){

    }
}
