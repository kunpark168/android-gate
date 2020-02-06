package com.anhtam.gate9.v2.discussion.common.discussion

import com.anhtam.domain.v2.Article
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.article.ArticleEntity
import com.anhtam.gate9.adapter.article.ArticleImageAdapter
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionFragment
import com.anhtam.gate9.v2.discussion.common.data.DataViewModel

class DiscussionGameFragment : CommonDiscussionFragment<ArticleEntity, ArticleImageAdapter, DataViewModel>() {

    private var mLink: String = ""
    override val colorTextTab = R.color.colorTabDiscussion
    override val tabTitle: List<Int>
        get() = arrayListOf(R.string.number_all, R.string.number_post, R.string.comment)

    companion object {
        fun newInstance(link: String) : DiscussionGameFragment {
            val fragment = DiscussionGameFragment()
            fragment.mLink = link
            return fragment
        }
    }
}
