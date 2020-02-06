package com.anhtam.gate9.v2.discussion.common.data

import androidx.fragment.app.viewModels
import com.anhtam.domain.v2.Article
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.article.ArticleEntity
import com.anhtam.gate9.adapter.article.ArticleImageAdapter
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionFragment

class DataFragment: CommonDiscussionFragment<ArticleEntity, ArticleImageAdapter, DataViewModel>() {
    private var mUserId: Int = 0
    override val tabTitle: List<Int>
        get() = arrayListOf(R.string.news_category, R.string.tip_category, R.string.video_category, R.string.image_category)
    override val colorTextTab: Int = R.color.colorTabData
    override val mViewModel: DataViewModel by viewModels { vmFactory }
    companion object {
        fun newInstance(userId: Int) : DataFragment {
            val fragment = DataFragment()
            fragment.mUserId = userId
            return fragment
        }
    }

}
