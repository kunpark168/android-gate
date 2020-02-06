package com.anhtam.gate9.v2.discussion.common.rating

import androidx.fragment.app.viewModels
import com.anhtam.domain.v2.Post
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.PostAdapter
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionFragment
import com.anhtam.gate9.vo.model.Category
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.fragment_rating.*

class RatingFragment: CommonDiscussionFragment<Post, PostAdapter>() {

    override fun onTabChanged(id: Int) {

    }

    private var mUserId: Int = 0
    private var mType = Category.Member
    private val viewModel: RatingViewModel by viewModels { vmFactory }

    override val colorTextTab: Int = R.color.colorTabRating

    override fun inflateLayout() = R.layout.fragment_rating

    override fun loadData() {
        viewModel.requestFirst(mUserId)
    }

    override fun initView() {
        super.initView()
        ratingComponent?.ratingInfo(mType)
        ratingComponent?.initView(arrayOf(0.01f, 0.01f, 0.02f, 0.01f, 0.45f))
    }

    override fun configTabLayout() {
        tabLayout.apply {
            repeat(4) {
                addTab(newTab())
            }
        }
        updateTabLayout()
    }

    override fun updateTabLayout() {
        tabLayout.getTabAt(0)?.text = Phrase.from(resources.getString(R.string.me_category))
                .put("amount", 0).format()
        tabLayout.getTabAt(1)?.text = Phrase.from(resources.getString(R.string.game_category))
                .put("amount", 0).format()
        tabLayout.getTabAt(2)?.text = Phrase.from(resources.getString(R.string.publisher_category))
                .put("amount", 0).format()
        tabLayout.getTabAt(3)?.text = Phrase.from(resources.getString(R.string.gamer_category))
                .put("amount", 0).format()
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