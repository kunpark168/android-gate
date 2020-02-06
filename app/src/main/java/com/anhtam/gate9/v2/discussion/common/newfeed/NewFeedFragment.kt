package com.anhtam.gate9.v2.discussion.common.newfeed

import androidx.fragment.app.viewModels
import com.anhtam.domain.v2.Post
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.PostAdapter
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionFragment
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.shared_discussion_layout.*
import kotlin.math.roundToLong

class NewFeedFragment : CommonDiscussionFragment<Post, PostAdapter, NewFeedViewModel>() {
    override fun onTabChanged(id: Int) {
        when(id) {
            0 -> {
                newRequestType(PostCategory.BOTH)
            }
            1 -> {
                newRequestType(PostCategory.POST)
            }
            else -> {
                newRequestType(PostCategory.COMMENT)
            }
        }
    }

    private var mCurrentCategory: PostCategory = PostCategory.BOTH
    override val colorTextTab = R.color.colorTabDiscussion
    override val mViewModel: NewFeedViewModel by viewModels { vmFactory }
    override var mLazyLoad = false

    private var mCountTab1: Double = 0.0
    private var mCountTab2: Double = 0.0
    private var mCountTab3: Double = 0.0

    override fun loadData() {
        mViewModel.loadData(mCurrentCategory.category, refresh = true)
    }

    override fun onAttachUser(user: User) {
        super.onAttachUser(user)
        mViewModel.initialize(user)
    }

    override fun observer() {
        super.observer()
    }
    override fun configTabLayout() {
        tabLayout.apply {
            repeat(3) {
                addTab(newTab())
            }
        }
        updateTabLayout()
    }

    override fun updateTabLayout() {
        tabLayout.getTabAt(0)?.text = Phrase.from(resources.getString(R.string.number_all))
                .put("amount", mCountTab1.roundToLong().toString()).format()
        tabLayout.getTabAt(1)?.text = Phrase.from(resources.getString(R.string.number_post))
                .put("amount", mCountTab2.roundToLong().toString()).format()
        tabLayout.getTabAt(2)?.text = Phrase.from(resources.getString(R.string.number_comment))
                .put("amount", mCountTab3.roundToLong().toString()).format()
    }

    override fun loadMore() {
        super.loadMore()
        mViewModel.loadData()
    }

    private fun newRequestType(category: PostCategory) {
        mCurrentCategory = category
        mAdapter.data.clear()
        mAdapter.notifyDataSetChanged()
        if (mViewModel.mCategory != category.category) {
            mViewModel.loadData(category.category, refresh = true)
        }
    }
}
