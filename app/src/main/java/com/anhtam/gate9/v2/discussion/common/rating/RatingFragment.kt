package com.anhtam.gate9.v2.discussion.common.rating

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.RatingAdapter
import com.anhtam.gate9.navigation.FragmentResultListener
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionFragment
import com.anhtam.gate9.v2.shared.RatingComponent
import com.anhtam.gate9.vo.Rating
import com.anhtam.gate9.vo.model.Category
import of.bum.network.helper.RestResponse

class RatingFragment: CommonDiscussionFragment<Rating, RatingAdapter, RatingViewModel>(),FragmentResultListener {

    override fun onFragmentResult(args: Bundle) {
        loadData()
    }

    private var mUserId: Int = 0
    private var mType = Category.Member
    override val tabTitle: List<Int>
        get() = arrayListOf(R.string.me_category, R.string.game_category, R.string.publisher_category, R.string.gamer_category)
    override val colorTextTab: Int = R.color.colorTabRating
    override val mViewModel: RatingViewModel by viewModels { vmFactory }
    private var mRatingView: RatingComponent? = null
    override fun inflateLayout() = R.layout.fragment_rating
    private var mUser: User? = null


    override fun onLoadUser(user: User) {
        super.onLoadUser(user)
        mAdapter.setUser(user)
        mUser = user
    }

    override fun onTabChanged() {
        super.onTabChanged()
        mAdapter.setCategory(mCurrentCategory)
    }


    override fun initView() {
        super.initView()
        mRatingView = RatingComponent(context)
        mAdapter.setHeaderView(mRatingView)
        mRatingView?.onRatingButtonClicked() {
            navigation?.addFragment(com.anhtam.gate9.v2.danh_gia.RatingFragment.newInstance(mUserId, isUser = true, rating = it))
        }
    }

    override fun onResponseSuccess(data: RestResponse<*>?) {
        super.onResponseSuccess(data)
        val rate = data?.mMeta?.get("rate") as? Map<String, Any>
        val point1 = rate?.get("point1") as? Double ?: 0.0
        val point2 = rate?.get("point2") as? Double ?: 0.0
        val point3 = rate?.get("point3") as? Double ?: 0.0
        val point4 = rate?.get("point4") as? Double ?: 0.0
        val point5 = rate?.get("point5") as? Double ?: 0.0
        mRatingView?.ratingInfo(mType)
        mRatingView?.initView(arrayOf(point1, point2, point3, point4, point5))
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