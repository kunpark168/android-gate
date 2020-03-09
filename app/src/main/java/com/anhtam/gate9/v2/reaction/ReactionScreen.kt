package com.anhtam.gate9.v2.reaction

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.SharePageAdapter
import com.anhtam.gate9.utils.debounceClick
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.reaction_screen.*
import java.util.*

class ReactionScreen private constructor(
        private val mCommentId: Int,
        private val mUser: User
): DaggerNavigationFragment(R.layout.reaction_screen) {

    companion object {
        private const val CODE_LIKE = 1
        private const val CODE_DISLIKE = 2
        private const val CODE_LOVE = 3
        private const val CODE_COMMENT = 4
        const val CODE_VIEW = 5
        fun newInstance(commentId: Int, user: User) = ReactionScreen(commentId, user)
    }

    private lateinit var mAdapter: SharePageAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initControl()
    }

    override fun menuRes() = R.menu.menu_chat_search_more

    private fun initViewPager(){
        val context = context ?: return
        imgShare?.setColorFilter(ContextCompat.getColor(context, R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
        val fragments = ArrayList<Fragment>()
        fragments.add(ReactionFragment.newInstance(mCommentId, CODE_VIEW, mUser))
        fragments.add(ReactionFragment.newInstance(mCommentId, CODE_LOVE, mUser))
        fragments.add(ReactionFragment.newInstance(mCommentId, CODE_LIKE, mUser))
        fragments.add(ReactionFragment.newInstance(mCommentId, CODE_DISLIKE, mUser))
        fragments.add(ReactionFragment.newInstance(mCommentId, CODE_COMMENT, mUser))
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
        csShare.debounceClick { vpReaction.currentItem = 0 }
        csLove.debounceClick { vpReaction.currentItem = 1 }
        csLike.debounceClick { vpReaction.currentItem = 2 }
        csDisLike.debounceClick { vpReaction.currentItem = 3 }
        csComment.debounceClick { vpReaction.currentItem = 4 }
    }

    private fun onTabSelected(tab: Int){
        val context = context ?: return
        imgShare?.setColorFilter(if (tab == 0){
            ContextCompat.getColor(context, R.color.color_main_blue)
        }else{
            ContextCompat.getColor(context, R.color.color_react_grey)
        }, PorterDuff.Mode.MULTIPLY)
        imgLike?.setColorFilter(if (tab == 2){
            ContextCompat.getColor(context, R.color.color_main_blue)
        }else{
            ContextCompat.getColor(context, R.color.color_react_grey)
        }, PorterDuff.Mode.MULTIPLY)
        imgFavorite?.setColorFilter(if (tab == 1){
            ContextCompat.getColor(context, R.color.color_main_blue)
        }else{
            ContextCompat.getColor(context, R.color.color_react_grey)
        }, PorterDuff.Mode.MULTIPLY)
        imgDislike?.setColorFilter(if (tab == 3){
            ContextCompat.getColor(context, R.color.color_main_blue)
        }else{
            ContextCompat.getColor(context, R.color.color_react_grey)
        }, PorterDuff.Mode.MULTIPLY)
        imgComment?.setColorFilter(if (tab == 4){
            ContextCompat.getColor(context, R.color.color_main_blue)
        }else{
            ContextCompat.getColor(context, R.color.color_react_grey)
        }, PorterDuff.Mode.MULTIPLY)
    }
}
