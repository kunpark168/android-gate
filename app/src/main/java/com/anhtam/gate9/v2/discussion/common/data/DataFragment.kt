package com.anhtam.gate9.v2.discussion.common.data

import androidx.fragment.app.viewModels
import com.anhtam.domain.v2.Article
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.article.ArticleImageAdapter
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionFragment
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.shared_discussion_layout.*

class DataFragment: CommonDiscussionFragment<Article, ArticleImageAdapter, DataViewModel>() {
    override fun loadData() {
        viewModel.loadData(mCurrentCategory.category, refresh = true)
    }

    private var mUserId: Int = 0
    private val viewModel: DataViewModel by viewModels { vmFactory }
    private var mCurrentCategory = DataCategory.NEWS

    private var mCountTab1 = 0
    private var mCountTab2 = 0
    private var mCountTab3 = 0
    private var mCountTab4 = 0

    override val colorTextTab: Int = R.color.colorTabData

    override fun configTabLayout() {
        tabLayout.apply {
            repeat(4) {
                addTab(newTab())
            }
        }
        updateTabLayout()
    }

    override fun updateTabLayout() {
        tabLayout.getTabAt(0)?.text = Phrase.from(resources.getString(R.string.news_category))
                .put("amount", mCountTab1).format()
        tabLayout.getTabAt(1)?.text = Phrase.from(resources.getString(R.string.tip_category))
                .put("amount", mCountTab2).format()
        tabLayout.getTabAt(2)?.text = Phrase.from(resources.getString(R.string.video_category))
                .put("amount", mCountTab3).format()
        tabLayout.getTabAt(3)?.text = Phrase.from(resources.getString(R.string.image_category))
                .put("amount", mCountTab4).format()
    }

    override fun onTabChanged(id: Int) {
        when(id) {
            0 -> {
                newRequestType(DataCategory.NEWS)
            }
            1 -> {
                newRequestType(DataCategory.TIP)
            }
            2 -> {
                newRequestType(DataCategory.VIDEO)
            }
            else -> {
                newRequestType(DataCategory.IMAGE)
            }
        }
    }

    private fun newRequestType(category: DataCategory) {
        mCurrentCategory = category
        mAdapter.data.clear()
        mAdapter.notifyDataSetChanged()
        if (viewModel.mCategory != category.category) {
            viewModel.loadData(category.category)
        }
    }

    companion object {
        fun newInstance(userId: Int) : DataFragment {
            val fragment = DataFragment()
            fragment.mUserId = userId
            return fragment
        }
    }

}
