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
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.vo.Reactions
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.reaction_screen.*
import java.util.*

class ReactionScreen private constructor(
        private val mCommentId: Int,
        private val mUser: User,
        private val mReactions: Reactions
): DaggerNavigationFragment(R.layout.reaction_screen) {


    companion object {
        private const val CODE_LIKE = 1
        private const val CODE_DISLIKE = 2
        private const val CODE_LOVE = 3
        private const val CODE_COMMENT = 4
        const val CODE_VIEW = 5
        fun newInstance(commentId: Int, user: User, reactions: Reactions) = ReactionScreen(commentId, user, reactions)
    }

    private var mPrevious = 0
    private lateinit var mAdapter: SharePageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initControl()
    }

    override fun menuRes() = R.menu.menu_chat_search_more

    private fun initViewPager(){
        viewTextView?.text = mReactions.mView.toString()
        favoriteTextView?.text = mReactions.mLove.toString()
        likeTextView?.text = mReactions.mLike.toString()
        dislikeTextView?.text = mReactions.mDislike.toString()
        commentTextView?.text = mReactions.mComment.toString()

        val context = context ?: return
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
                if (position == mPrevious) return
                when(mPrevious) {
                    0 -> {
                        Glide.with(context).load(R.drawable.ic_view_detail).into(viewIcon)
                    }
                    1 -> {
                        Glide.with(context).load(R.drawable.ic_reaction_love).into(favoriteIcon)
                    }
                    2 -> {
                        Glide.with(context).load(R.drawable.ic_like_post).into(likeIcon)
                    }
                    3 -> {
                        Glide.with(context).load(R.drawable.ic_dislike_post).into(dislikeIcon)
                    }
                    4 -> {
                        Glide.with(context).load(R.drawable.ic_chat_gray).into(commentIcon)
                    }
                }
                mPrevious = position
                when(position) {
                    0 -> {
                        Glide.with(context).load(R.drawable.viewed).into(viewIcon)
                    }
                    1 -> {
                        Glide.with(context).load(R.drawable.loved).into(favoriteIcon)
                    }
                    2 -> {
                        Glide.with(context).load(R.drawable.btnlike).into(likeIcon)
                    }
                    3 -> {
                        Glide.with(context).load(R.drawable.dislike_selected).into(dislikeIcon)
                    }
                    4 -> {
                        Glide.with(context).load(R.drawable.comment_selected).into(commentIcon)
                    }
                }
            }

        })
    }

    private fun initControl(){
        csShare.setOnClickListener { vpReaction.currentItem = 0 }
        csLove.setOnClickListener { vpReaction.currentItem = 1 }
        csLike.setOnClickListener { vpReaction.currentItem = 2 }
        csDisLike.setOnClickListener { vpReaction.currentItem = 3 }
        csComment.setOnClickListener { vpReaction.currentItem = 4 }
    }
}
