package com.anhtam.gate9.ui.discussion.common.rating

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.ReviewAdapter
import com.anhtam.gate9.ui.discussion.common.CommonDiscussionFragment
import com.anhtam.gate9.utils.loadingToAdapter
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.fragment_rating.*
import kotlinx.android.synthetic.main.shared_discussion_layout.rvShareDiscussion
import kotlinx.android.synthetic.main.shared_discussion_layout.tabLayout

class RatingFragment: CommonDiscussionFragment() {

    private var mUserId: Int = 0
    private var mType = Category.Member
    private lateinit var mAdapter: ReviewAdapter
    private val viewModel: RatingViewModel by viewModels { vmFactory }

    override val colorTextTab: Int = R.color.colorTabRating

    override fun inflateLayout() = R.layout.fragment_rating

    override fun loadData() {
        viewModel.requestFirst(mUserId)
    }

    override fun initView() {
        super.initView()
        initRv()
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

    private fun initRv() {
        mAdapter = ReviewAdapter(Glide.with(context!!))
        rvShareDiscussion?.adapter = mAdapter
    }

    override fun observer() {
        super.observer()
        viewModel.reviews.observe(this, Observer {
            it.loadingToAdapter(mAdapter, viewModel.page)
        })
    }

    override fun initEvents() {

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