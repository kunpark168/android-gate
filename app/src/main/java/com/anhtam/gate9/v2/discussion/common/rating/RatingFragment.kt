package com.anhtam.gate9.v2.discussion.common.rating

import com.anhtam.domain.v2.Post
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.PostAdapter
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionFragment
import com.anhtam.gate9.v2.discussion.common.newfeed.NewFeedViewModel
import com.anhtam.gate9.vo.model.Category
import kotlinx.android.synthetic.main.fragment_rating.*

class RatingFragment: CommonDiscussionFragment<Post, PostAdapter, NewFeedViewModel>() {

    private var mUserId: Int = 0
    private var mType = Category.Member
    override val tabTitle: List<Int>
        get() = arrayListOf(R.string.me_category, R.string.game_category, R.string.publisher_category, R.string.gamer_category)
    override val colorTextTab: Int = R.color.colorTabRating

    override fun inflateLayout() = R.layout.fragment_rating


    override fun initView() {
        super.initView()
        ratingComponent?.ratingInfo(mType)
        ratingComponent?.initView(arrayOf(0.01f, 0.01f, 0.02f, 0.01f, 0.45f))
    }

    companion object {
        fun newInstance(userId: Int, type: Category) : RatingFragment {
            val fragment = RatingFragment()
            fragment.mUserId = userId
            fragment.mType = type
            return fragment
        }
    }

}