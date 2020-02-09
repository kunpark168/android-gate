package com.anhtam.gate9.v2.discussion.common.rating

import androidx.fragment.app.viewModels
import com.anhtam.domain.v2.Post
import com.anhtam.domain.v2.Rating
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.PostAdapter
import com.anhtam.gate9.adapter.v2.RatingAdapter
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionFragment
import com.anhtam.gate9.v2.shared.RatingComponent
import com.anhtam.gate9.vo.model.Category

class RatingFragment: CommonDiscussionFragment<Rating, RatingAdapter, RatingViewModel>() {

    private var mUserId: Int = 0
    private var mType = Category.Member
    override val tabTitle: List<Int>
        get() = arrayListOf(R.string.me_category, R.string.game_category, R.string.publisher_category, R.string.gamer_category)
    override val colorTextTab: Int = R.color.colorTabRating
    override val mViewModel: RatingViewModel by viewModels { vmFactory }
    private var mRatingView: RatingComponent? = null
    override fun inflateLayout() = R.layout.fragment_rating


    override fun onLoadUser(user: User) {
        super.onLoadUser(user)
        val rand = (0..100).random() / 100f
        mAdapter.setUser(user)
        mRatingView?.ratingInfo(mType)
        mRatingView?.initView(arrayOf(rand, (1-rand) / 4, (1-rand) / 4, (1-rand) / 4, (1-rand) / 4))
    }

    override fun initView() {
        super.initView()
        mRatingView = RatingComponent(context)
        mAdapter.setHeaderView(mRatingView)
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