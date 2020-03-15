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
                        viewTextView?.text = getString(R.string.view_label)
                    }
                    1 -> {
                        Glide.with(context).load(R.drawable.ic_reaction_love).into(favoriteIcon)
                        favoriteTextView?.text = getString(R.string.favorite_label)
                    }
                    2 -> {
                        Glide.with(context).load(R.drawable.ic_like_post).into(likeIcon)
                        likeTextView?.text = getString(R.string.like_label)
                    }
                    3 -> {
                        Glide.with(context).load(R.drawable.ic_dislike_post).into(dislikeIcon)
                        dislikeTextView?.text = getString(R.string.dislike_label)
                    }
                    4 -> {
                        Glide.with(context).load(R.drawable.ic_chat_gray).into(commentIcon)
                        commentTextView?.text = getString(R.string.comment)
                    }
                }
                mPrevious = position
                when(position) {
                    0 -> {
                        Glide.with(context).load(R.drawable.viewed).into(viewIcon)
                        viewTextView?.text = mReactions.mView.toString()
                    }
                    1 -> {
                        Glide.with(context).load(R.drawable.loved).into(favoriteIcon)
                        favoriteTextView?.text = mReactions.mView.toString()
                    }
                    2 -> {
                        Glide.with(context).load(R.drawable.btnlike).into(likeIcon)
                        likeTextView?.text = mReactions.mView.toString()
                    }
                    3 -> {
                        Glide.with(context).load(R.drawable.dislike_selected).into(dislikeIcon)
                        dislikeTextView?.text = mReactions.mView.toString()
                    }
                    4 -> {
                        Glide.with(context).load(R.drawable.comment_selected).into(commentIcon)
                        commentTextView?.text = mReactions.mView.toString()
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
