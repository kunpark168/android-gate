package com.anhtam.gate9.ui.reaction

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.anhtam.gate9.adapter.SharePageAdapter
import com.anhtam.gate9.R
import com.anhtam.gate9.ui.base.BaseActivity
import com.anhtam.gate9.utils.debounceClick
import kotlinx.android.synthetic.main.activity_feelling.*
import java.util.*

class ReactionActivity : BaseActivity() {

    private lateinit var mAdapter: SharePageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feelling)
        initControl()
        initViewPager()
    }

    companion object{
        fun start(context: Context?){
            val intent = Intent(context, ReactionActivity::class.java)
            context?.startActivity(intent)
        }
    }

    private fun initViewPager(){
        val fragments = ArrayList<Fragment>()
//        fragments.add(ReactionFragment.newInstance(TypeGameTab.ALL)) TODO v2
//        fragments.add(ReactionFragment.newInstance(TypeGameTab.FAVOURITE))
//        fragments.add(ReactionFragment.newInstance(TypeGameTab.DOWNLOADED))
//        fragments.add(ReactionFragment.newInstance(TypeGameTab.FOLLOWING))
//        fragments.add(ReactionFragment.newInstance(TypeGameTab.FOLLOWING))
        mAdapter = SharePageAdapter(supportFragmentManager, fragments)
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
        csBack.debounceClick { onBackPressed() }

        csShare.debounceClick { vpReaction.currentItem = 0 }
        csLove.debounceClick { vpReaction.currentItem = 1 }
        csLike.debounceClick { vpReaction.currentItem = 2 }
        csDisLike.debounceClick { vpReaction.currentItem = 3 }
        csComment.debounceClick { vpReaction.currentItem = 4 }
    }

    private fun onTabSelected(tab: Int){

    }
}
